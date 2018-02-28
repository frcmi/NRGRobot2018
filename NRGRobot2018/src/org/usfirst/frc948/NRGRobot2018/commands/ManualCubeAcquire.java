package org.usfirst.frc948.NRGRobot2018.commands;

import org.usfirst.frc948.NRGRobot2018.RobotMap;
import org.usfirst.frc948.NRGRobot2018.utilities.MathUtil;
import org.usfirst.frc948.NRGRobot2018.OI;
import org.usfirst.frc948.NRGRobot2018.Robot;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Allows humans to acquire the cubes
 */
public class ManualCubeAcquire extends Command {

    public ManualCubeAcquire() {
        requires(Robot.cubeAcquirer);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// xbox joystick y values need to be inverted
    	double leftPower = MathUtil.deadband(-OI.xboxController.getY(Hand.kLeft), 0.1);
    	double rightPower = MathUtil.deadband(-OI.xboxController.getY(Hand.kRight), 0.1);
    	Robot.cubeAcquirer.rawAcquire(leftPower, rightPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.cubeAcquirer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}