// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package org.usfirst.frc948.NRGRobot2018;

import java.util.ArrayList;

import org.usfirst.frc948.NRGRobot2018.Robot.AutoMovement;
import org.usfirst.frc948.NRGRobot2018.Robot.AutoPosition;
import org.usfirst.frc948.NRGRobot2018.commandGroups.AutonomousRoutine;
import org.usfirst.frc948.NRGRobot2018.subsystems.Climber;
import org.usfirst.frc948.NRGRobot2018.subsystems.CubeAcquirer;
import org.usfirst.frc948.NRGRobot2018.subsystems.CubeLifter;
import org.usfirst.frc948.NRGRobot2018.subsystems.CubeTilter;
import org.usfirst.frc948.NRGRobot2018.subsystems.Drive;
import org.usfirst.frc948.NRGRobot2018.utilities.PositionTracker;
import org.usfirst.frc948.NRGRobot2018.utilities.CubeCalculations;
import org.usfirst.frc948.NRGRobot2018.utilities.PreferenceKeys;
import org.usfirst.frc948.NRGRobot2018.vision.PixyCam.Block;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static SendableChooser<AutoPosition> autoPositionChooser;
	public static SendableChooser<AutoMovement> autoMovementChooser;

	Command autonomousCommand;
	public static Preferences preferences;
	public static OI oi;
	public static Drive drive;
	public enum AutoPosition {
		RED_LEFT, RED_CENTER, RED_RIGHT, BLUE_LEFT, BLUE_CENTER, BLUE_RIGHT
	}
	public enum AutoMovement {
		RIGHT_SWITCH, LEFT_SWITCH, LEFT_SCALE, RIGHT_SCALE
	}
	public static CubeAcquirer cubeAcquirer;
	public static CubeLifter cubeLifter;
	public static CubeTilter cubeTilter;
	public static Climber climber;
	public static PositionTracker positionTracker;
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("robotInit() started");
		preferences = Preferences.getInstance();
		RobotMap.init();

		preferences = Preferences.getInstance();
		drive = new Drive();
		cubeAcquirer = new CubeAcquirer();
		cubeLifter = new CubeLifter();
		cubeTilter  = new CubeTilter();
		climber = new Climber();
		oi = new OI();
		positionTracker = new PositionTracker(0,0);

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.

		initPreferences();
		RobotMap.pixy.startVisionThread();
		System.out.println("robotInit() done");
		
		autoPositionChooser = new SendableChooser<AutoPosition>();
		autoPositionChooser.addDefault("Red left", AutoPosition.RED_LEFT);
		autoPositionChooser.addObject("Red center", AutoPosition.RED_CENTER);
		autoPositionChooser.addObject("Red right", AutoPosition.RED_RIGHT);
		autoPositionChooser.addObject("Blue left", AutoPosition.BLUE_LEFT);
		autoPositionChooser.addObject("Blue center", AutoPosition.BLUE_CENTER);
		autoPositionChooser.addObject("Blue right", AutoPosition.BLUE_RIGHT);

		autoMovementChooser = new SendableChooser<AutoMovement>();
		autoMovementChooser.addObject("Left Scale", AutoMovement.LEFT_SCALE);
		autoMovementChooser.addObject("Left Switch", AutoMovement.LEFT_SWITCH);
		autoMovementChooser.addObject("Right Switch", AutoMovement.RIGHT_SWITCH);
		autoMovementChooser.addDefault("Right Scale", AutoMovement.RIGHT_SCALE);

		SmartDashboard.putData("Choose autonomous position", autoPositionChooser);
		SmartDashboard.putData("Choose autonomous movement", autoMovementChooser);
	}

	/**
	 * This function is called when the disabled button is hit. You can use it to
	 * reset subsystems before shutting down.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();

	}

	@Override
	public void autonomousInit() {
		// schedule the autonomous command (example)
		System.out.println("autoInit()");
		autonomousCommand = new AutonomousRoutine();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testPeriodic() {
		// TODO Auto-generated method stub

	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("navx gyro yaw", RobotMap.navx.getYaw());

		ArrayList<Block> currFrame = RobotMap.pixy.getPixyFrameData();
		
		if (currFrame.size() > 0) {
			Block cube = currFrame.get(0);
			
			SmartDashboard.putString("Cube", cube.toString());
			SmartDashboard.putNumber("Angle to turn", CubeCalculations.getAngleToTurn(cube));
			SmartDashboard.putNumber("Inches to cube from width", CubeCalculations.getDistanceFromWidth(cube));
		}

		positionTracker.updatePosition();
		SmartDashboard.putNumber("PositionTracker current x", positionTracker.getX());
		SmartDashboard.putNumber("PositionTracker current y", positionTracker.getY());
		SmartDashboard.putNumber("xEncoder", RobotMap.xEncoder.getDistance());
		SmartDashboard.putNumber("yEncoder", RobotMap.yEncoder.getDistance());
		SmartDashboard.putNumber("cubeLiftEncoder", RobotMap.cubeLiftEncoder.getDistance());
		SmartDashboard.putNumber("cubeTilttEncoder", RobotMap.cubeTiltEncoder.getDistance());
		SmartDashboard.putData("Limit5", RobotMap.lifterUpperLimitSwitch);
	}

	public void initPreferences() {
		if (preferences.getBoolean(PreferenceKeys.WRITE_DEFAULT, true)) {
			preferences.putDouble(PreferenceKeys.TURN_P_TERM, Drive.DEFAULT_TURN_P);
			preferences.putDouble(PreferenceKeys.TURN_I_TERM, Drive.DEFAULT_TURN_I);
			preferences.putDouble(PreferenceKeys.TURN_D_TERM, Drive.DEFAULT_TURN_D);
			preferences.putDouble(PreferenceKeys.MAX_VEL_CHANGE, Drive.DEF_MAX_VEL_CHANGE);
			
			preferences.putDouble(PreferenceKeys.DRIVE_X_P, 1.0/6.0);
			preferences.putDouble(PreferenceKeys.DRIVE_X_I, 0.0);
			preferences.putDouble(PreferenceKeys.DRIVE_X_D, 0.0);
			preferences.putDouble(PreferenceKeys.DRIVE_X_MAX_POWER, 0.9);
			
			preferences.putDouble(PreferenceKeys.DRIVE_Y_P, 1.0/15.0);
			preferences.putDouble(PreferenceKeys.DRIVE_Y_I, 0.0);
			preferences.putDouble(PreferenceKeys.DRIVE_Y_D, 0.0);
			preferences.putDouble(PreferenceKeys.DRIVE_Y_MAX_POWER, 0.5);
			

			preferences.putDouble(PreferenceKeys.DRIVE_TURN_P, 1.0/12.0);
			preferences.putDouble(PreferenceKeys.DRIVE_TURN_I, 0.0);
			preferences.putDouble(PreferenceKeys.DRIVE_TURN_D, 0.0);
			preferences.putDouble(PreferenceKeys.DRIVE_TURN_MAX_POWER, 0.4);
			
			preferences.putDouble(PreferenceKeys.DRIVE_XYH_X, 48.0);
			preferences.putDouble(PreferenceKeys.DRIVE_XYH_Y, 48.0);
			preferences.putDouble(PreferenceKeys.DRIVE_XYH_H, 0);
			preferences.putDouble(PreferenceKeys.DRIVE_XYH_X_POWER, 0.9);
			preferences.putDouble(PreferenceKeys.DRIVE_XYH_Y_POWER, 0.5);
			preferences.putDouble(PreferenceKeys.DRIVE_XYH_TURN_POWER, 0.3);
			
			preferences.putBoolean(PreferenceKeys.WRITE_DEFAULT, false);

			
		}
	}
}
