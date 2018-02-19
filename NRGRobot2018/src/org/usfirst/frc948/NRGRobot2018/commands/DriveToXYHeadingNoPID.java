package org.usfirst.frc948.NRGRobot2018.commands;

import org.usfirst.frc948.NRGRobot2018.Robot;
import org.usfirst.frc948.NRGRobot2018.RobotMap;
import org.usfirst.frc948.NRGRobot2018.utilities.MathUtil;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DriveToXYHeadingNoPID: Drives/strafes to target x and y (inches), and heading (degrees)
 */
public class DriveToXYHeadingNoPID extends Command {
	final double X_DISTANCE_TO_SLOW_DOWN = 6.0; // in inches
	final double Y_DISTANCE_TO_SLOW_DOWN = 15.0;
	final double DISTANCE_TOLERANCE = 5.0;
	
	final double ANGLE_TO_SLOW_DOWN = 20.0; // in degrees
	final double ANGLE_TOLERANCE = 5.0;
	
	double desiredX; // desired x
	double desiredY; // desired y
	double desiredHeading; // desired heading
	double xMaxPower;
	double yMaxPower;
	double turnMaxPower;
	
	private double dXFieldFrame; // (desired x) - (current robot x position)
	private double dYFieldFrame; // (desired y) - (current robot y position)
	
	private double dHeadingToTargetHeading; // (desired heading) - (current robot heading)
	
	public DriveToXYHeadingNoPID(double x, double y, double heading, 
			double xMaxPower, double yMaxPower, double turnMaxPower) {
		requires(Robot.drive);

		desiredX = x;
		desiredY = y;
		desiredHeading = heading;
		this.xMaxPower = Math.abs(xMaxPower);
		this.yMaxPower = Math.abs(yMaxPower);
		this.turnMaxPower = Math.abs(turnMaxPower);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		dXFieldFrame = Double.MAX_VALUE;
		dYFieldFrame = Double.MAX_VALUE;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// used for calculating powers in x and y directions
		double currX = Robot.positionTracker.getX();
		double currY = Robot.positionTracker.getY();
		double currHeading = RobotMap.gyro.getAngle();
		
		dXFieldFrame = desiredX - currX;
		dYFieldFrame = desiredY - currY;
		double distanceToTarget = Math.sqrt(dXFieldFrame * dXFieldFrame + dYFieldFrame * dYFieldFrame);
		
		double dHeadingToXY = Math.toDegrees(Math.atan2(dXFieldFrame, dYFieldFrame)) - currHeading;
		double dXRobotFrame = distanceToTarget * Math.sin(Math.toRadians(dHeadingToXY)); // desired x in robot coordinate frame
		double dYRobotFrame = distanceToTarget * Math.cos(Math.toRadians(dHeadingToXY)); // desired y in robot coordinate frame
		
		// used for calculating turning power
		dHeadingToTargetHeading = desiredHeading - currHeading;
		
		// calculating powers
		double xPower = MathUtil.clamp(dXRobotFrame / X_DISTANCE_TO_SLOW_DOWN, -xMaxPower, xMaxPower);
		double yPower = MathUtil.clamp(dYRobotFrame / Y_DISTANCE_TO_SLOW_DOWN, -yMaxPower, yMaxPower);
		double turnPower = MathUtil.clamp(dHeadingToTargetHeading / ANGLE_TO_SLOW_DOWN, -turnMaxPower, turnMaxPower);

		// sending calculated powers
		Robot.drive.rawDriveCartesian(xPower, yPower, turnPower);

		SmartDashboard.putNumber("driveToXYHeading/dX", dXFieldFrame);
		SmartDashboard.putNumber("driveToXYHeading/dY", dYFieldFrame);
		SmartDashboard.putNumber("driveToXYHeading/dHeading", dHeadingToXY);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (Math.abs(dXFieldFrame) <= DISTANCE_TOLERANCE && 
				Math.abs(dYFieldFrame) <= DISTANCE_TOLERANCE && 
				Math.abs(dHeadingToTargetHeading) <= ANGLE_TOLERANCE);
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
