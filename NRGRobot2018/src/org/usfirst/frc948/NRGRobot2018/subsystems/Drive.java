// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc948.NRGRobot2018.subsystems;

import org.usfirst.frc948.NRGRobot2018.Robot;
import org.usfirst.frc948.NRGRobot2018.RobotMap;
import org.usfirst.frc948.NRGRobot2018.commands.ManualDrive;
import org.usfirst.frc948.NRGRobot2018.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Drive extends Subsystem implements PIDOutput {

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	
	private PIDController drivePIDController;
	private volatile double PIDOutput = 0;
	
	public final static double DEFAULT_TURN_P = 0.02;
	public final static double DEFAULT_TURN_I = 0.0;
	public final static double DEFAULT_TURN_D = 0.0;
	
	public final static double SCALE_HIGH = 1.0;
	public final static double SCALE_LOW = 0.5;
	public double scale = SCALE_LOW;
	public static final double DEF_MAX_VEL_CHANGE = 0.1;
	
	private double lastVelX = 0.0;
	private double lastVelY = 0.0;
	
	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

	@Override
	public void initDefaultCommand() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		setDefaultCommand(new ManualDrive());

		// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
	
	public void drivePIDControllerInit(double p, double i, double d, double setpoint, double tolerance) {
		drivePIDController = new PIDController(p, i, d, RobotMap.navxWrapper, this);
		drivePIDController.reset();
		
		drivePIDController.setOutputRange(-1, 1);
		drivePIDController.setSetpoint(setpoint);
		drivePIDController.setAbsoluteTolerance(tolerance);
		
		drivePIDController.enable();
	}
	
	public void turnToHeadingPIDInit(double desiredHeading, double tolerance) {
		drivePIDControllerInit(Robot.preferences.getDouble(PreferenceKeys.TURN_P_TERM, DEFAULT_TURN_P),
				Robot.preferences.getDouble(PreferenceKeys.TURN_I_TERM, DEFAULT_TURN_I),
				Robot.preferences.getDouble(PreferenceKeys.TURN_D_TERM, DEFAULT_TURN_D),
				desiredHeading,
				tolerance);
	}
	
	public void turnToHeadingPIDExecute(double desiredHeading) {
		double currentPIDOutput = PIDOutput;

		SmartDashboard.putNumber("Turn To Heading PID Error", drivePIDController.getError());
		SmartDashboard.putNumber("Turn To Heading PID Output", drivePIDController.getError());
		
		rawDriveCartesian(0, 0, currentPIDOutput);
	}
	
	public void turnToHeadingPIDEnd() {
		drivePIDController.reset();
		drivePIDController.free();
		drivePIDController = null;
		
		stop();
	}
	
	public void driveCartesian(double currVelX, double currVelY, double currRot) {
		double maxVelDifference = Robot.preferences.getDouble(PreferenceKeys.MAX_VEL_CHANGE, DEF_MAX_VEL_CHANGE);
		double velXChange = currVelX - lastVelX;
		double velYChange = currVelY - lastVelY;		
		
		if (Math.abs(velXChange) > maxVelDifference) {
			currVelX = lastVelX + Math.copySign(maxVelDifference, velXChange);
		}
		
		if (Math.abs(velYChange) > maxVelDifference) {
			currVelY = lastVelY + Math.copySign(maxVelDifference, velYChange);
		}
		
		rawDriveCartesian(currVelX, currVelY, currRot);
	}
	
	public void rawDriveCartesian(double velX, double velY, double rot) {
		lastVelX = velX;
		lastVelY = velY;
		
		velX *= scale;
		velY *= scale;
		rot *= scale;
		RobotMap.driveMecanumDrive.driveCartesian(velX, velY, rot);
	}

	public void stop() {
		lastVelX = 0;
		lastVelY = 0;
		
		RobotMap.driveMecanumDrive.stopMotor();
	}
	
	public void setScale(double s) {
		scale = s;
	}

	public boolean onTarget() {
		return drivePIDController.onTarget();
	}
	
	@Override
	public void periodic() {
		// Put code here to be run every loop

	}

	@Override
	public void pidWrite(double output) {
		PIDOutput = output;
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

}
