
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

import org.usfirst.frc948.NRGRobot2018.Robot.AutoMovement;
import org.usfirst.frc948.NRGRobot2018.Robot.AutoStartingPosition;
import org.usfirst.frc948.NRGRobot2018.commandGroups.DriveToCubeAndGrab;
import org.usfirst.frc948.NRGRobot2018.commandGroups.TiltAcquirerAndEject;
import org.usfirst.frc948.NRGRobot2018.commands.StrafeAlignWithCube;
import org.usfirst.frc948.NRGRobot2018.commands.DriveStraightDistance;
import org.usfirst.frc948.NRGRobot2018.commands.DriveToCube;
import org.usfirst.frc948.NRGRobot2018.commands.DriveToXYHeadingPIDTest;
import org.usfirst.frc948.NRGRobot2018.commands.InterruptCommands;
import org.usfirst.frc948.NRGRobot2018.commands.LiftToHeight;
import org.usfirst.frc948.NRGRobot2018.commands.ManualClimb;
import org.usfirst.frc948.NRGRobot2018.commands.ManualCubeLift;
import org.usfirst.frc948.NRGRobot2018.commands.ManualDrive;
import org.usfirst.frc948.NRGRobot2018.commands.ManualDriveStraight;
import org.usfirst.frc948.NRGRobot2018.commands.ManualStrafeStraight;
import org.usfirst.frc948.NRGRobot2018.commands.ResetSensors;
import org.usfirst.frc948.NRGRobot2018.commands.SetDriveScale;
import org.usfirst.frc948.NRGRobot2018.commands.TiltAcquirerDown;
import org.usfirst.frc948.NRGRobot2018.commands.TiltAcquirerToAngle;
import org.usfirst.frc948.NRGRobot2018.commands.TurnToHeading;
import org.usfirst.frc948.NRGRobot2018.subsystems.CubeLifter;
import org.usfirst.frc948.NRGRobot2018.subsystems.CubeTilter;
import org.usfirst.frc948.NRGRobot2018.subsystems.Drive;
import org.usfirst.frc948.NRGRobot2018.subsystems.Drive.Direction;
import org.usfirst.frc948.NRGRobot2018.utilities.MathUtil;
import org.usfirst.frc948.NRGRobot2018.utilities.PreferenceKeys;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands
 * and command groups that allow control of the robot.
 */
public class OI {
	public static final Joystick leftJoystick = new Joystick(0);
	public static final Joystick rightJoystick = new Joystick(1);
	public static final XboxController xboxController = new XboxController(2);
	public static final Joystick arduinoJoystick = new Joystick(3);

	public static final JoystickButton interruptCommands = new JoystickButton(leftJoystick, 6);
	public static final JoystickButton resetSensors = new JoystickButton(leftJoystick, 7);

	public static final JoystickButton leftShiftGears = new JoystickButton(leftJoystick, 1);
	public static final JoystickButton rightShiftGears = new JoystickButton(rightJoystick, 1);
	public static final JoystickButton driveStraight = new JoystickButton(leftJoystick, 2);
	public static final JoystickButton strafeStraight = new JoystickButton(leftJoystick, 3);
	
	// need to talk with driver about these button locations
	public static final JoystickButton driveToCubeAndGrab = new JoystickButton(rightJoystick, 2);
	public static final JoystickButton driveToCube = new JoystickButton(rightJoystick, 5);
	public static final JoystickButton strafeAlignWithCube = new JoystickButton(rightJoystick, 3);
	
	//xbox buttons
	public static final JoystickButton tiltAcquirerAndEjectCube = new JoystickButton(xboxController, 1); // 'A' Button
	
	// arduino buttons
	public static final JoystickButton climberButton = new JoystickButton(arduinoJoystick, 10);
	public static final JoystickButton autoLeft = new JoystickButton(arduinoJoystick, 9);
	public static final JoystickButton autoCenter = new JoystickButton(arduinoJoystick, 8);
	public static final JoystickButton autoRight = new JoystickButton(arduinoJoystick, 7);
	public static final JoystickButton autoSwitch = new JoystickButton(arduinoJoystick, 3);
	public static final JoystickButton autoScale = new JoystickButton(arduinoJoystick, 4);
	public static final JoystickButton autoBoth = new JoystickButton(arduinoJoystick, 5);
	public static final JoystickButton autoForward = new JoystickButton(arduinoJoystick, 2);
	public static final JoystickButton autoNone = new JoystickButton(arduinoJoystick, 1);

	private static boolean reverseTriggers = false;
	
	public enum PlateLocation {
		LEFT, RIGHT;
	}

	public static void init() {
		// Initialize commands after initializing buttons
		interruptCommands.whenPressed(new InterruptCommands());
		resetSensors.whenPressed(new ResetSensors());

		leftShiftGears.whenPressed(new SetDriveScale(Drive.SCALE_LOW));
		leftShiftGears.whenReleased(new SetDriveScale(Drive.SCALE_HIGH));
		rightShiftGears.whenPressed(new SetDriveScale(Drive.SCALE_LOW));
		rightShiftGears.whenReleased(new SetDriveScale(Drive.SCALE_HIGH));
		driveStraight.whileHeld(new ManualDriveStraight());
		strafeStraight.whileHeld(new ManualStrafeStraight());
		
		driveToCubeAndGrab.whenPressed(new DriveToCubeAndGrab());
		driveToCube.whenPressed(new DriveToCube(false));
		strafeAlignWithCube.whenPressed(new StrafeAlignWithCube());

		tiltAcquirerAndEjectCube.whenPressed(new TiltAcquirerAndEject(-133, 1, 0.5));
		climberButton.whileHeld(new ManualClimb(0.9));

		// SmartDashboard Buttons
		SmartDashboard.putData("Reset Sensors", new ResetSensors());

		SmartDashboard.putData("ManualDrive", new ManualDrive());
		SmartDashboard.putData("driveStraightDistance 20 feet", new DriveStraightDistance(1, 240, Direction.FORWARD));
		SmartDashboard.putData("Drive to XY Heading Test", new DriveToXYHeadingPIDTest());
		SmartDashboard.putData("StrafeStraightDistance 4 feet", new DriveStraightDistance(1, 48,Direction.RIGHT));
		SmartDashboard.putData("driveStraightDistanceBackward 4 feet", new DriveStraightDistance(0.5, 48, Direction.BACKWARD));
		
		SmartDashboard.putData("Drive to Cube", new DriveToCube(false));
		SmartDashboard.putData("Drive to Cube and grab", new DriveToCubeAndGrab());
		SmartDashboard.putData("Center to Cube", new StrafeAlignWithCube());

		SmartDashboard.putData("Turn To 90 Degrees", new TurnToHeading(90));
		SmartDashboard.putData("Turn To -90 Degrees", new TurnToHeading(-90));

		SmartDashboard.putData("Set to high gear", new SetDriveScale(Drive.SCALE_HIGH));
		SmartDashboard.putData("Set to low gear", new SetDriveScale(Drive.SCALE_LOW));
		
		SmartDashboard.putData("Lift to Scale?", new LiftToHeight(CubeLifter.SCALE_MEDIUM));
		SmartDashboard.putData("Lift to Switch?", new LiftToHeight(CubeLifter.SWITCH_LEVEL));
		SmartDashboard.putData("Set Lift height to zero?", new LiftToHeight(CubeLifter.STOWED));
		
		SmartDashboard.putData("Tilt acquirer and eject cube", new TiltAcquirerAndEject(45, 1, 0.5));
		SmartDashboard.putData("CubeTiltDown", new TiltAcquirerToAngle(CubeTilter.TILTER_DOWN));
		SmartDashboard.putData("CubeTiltUp", new TiltAcquirerToAngle(CubeTilter.TILTER_UP));
		SmartDashboard.putData("Tilt acquirer down", new TiltAcquirerDown(1));
	}

	public static double getRightJoystickX() {
		return rightJoystick.getX();
	}

	public static double getRightJoystickY() {
		return -rightJoystick.getY();
	}

	public static double getLeftJoystickX() {
		return leftJoystick.getX();
	}

	public static double getLeftJoystickY() {
		return -leftJoystick.getY();
	}

	// Only right JS is able to rotate
	public static double getRightJoystickRot() {
		return rightJoystick.getRawAxis(2);
	}

	public static double getXBoxLeftY() {
		return -OI.xboxController.getY(Hand.kLeft);
	}

	public static double getXBoxRightY() {
		return -OI.xboxController.getY(Hand.kRight);
	}

	public static double getXBoxTriggerL() {
		double triggerL = reverseTriggers ? 1 - xboxController.getRawAxis(2) :  xboxController.getRawAxis(2);
		return MathUtil.deadband(triggerL, 0.1);
	}

	public static double getXBoxTriggerR() {
		double triggerR = reverseTriggers ? 1 - xboxController.getRawAxis(3) :  xboxController.getRawAxis(3);
		return MathUtil.deadband(triggerR, 0.1);
	}
	
	public static void initTriggers() {
		reverseTriggers = OI.getXBoxTriggerL() > 0.1 && OI.getXBoxTriggerL() > 0.1;
	}
	
	public static boolean isXBoxDPadUp() {
		int pov = xboxController.getPOV();
		return pov == 0 || pov == 45 || pov == 315;
	}

	public static boolean isXBoxDPadDown() {
		int pov = xboxController.getPOV();
		return pov >= 135 && pov <= 225;
	}

	public static DriverStation.Alliance getAllianceColor() {
		return DriverStation.getInstance().getAlliance();
	}

	// These methods return the location of the alliance's switch/scale plates,
	// relative to the alliance facing the field from behind the alliance wall
	public static PlateLocation getAllianceSwitchSide() {
		return DriverStation.getInstance().getGameSpecificMessage().charAt(0) == 'L' ? PlateLocation.LEFT : PlateLocation.RIGHT;
	}

	public static PlateLocation getScaleSide() {
		return DriverStation.getInstance().getGameSpecificMessage().charAt(1) == 'L' ? PlateLocation.LEFT : PlateLocation.RIGHT;
	}

	public static PlateLocation getOpposingSwitchSide() {
		return DriverStation.getInstance().getGameSpecificMessage().charAt(2) == 'L' ? PlateLocation.LEFT : PlateLocation.RIGHT;
	}

	public static AutoStartingPosition getAutoStartingPosition() {
		AutoStartingPosition autoPosition = null;
		
		if (Robot.preferences.getBoolean(PreferenceKeys.USE_PHYSICAL_AUTO_CHOOSER, true)) {
			if (autoLeft.get()) {
				autoPosition = AutoStartingPosition.LEFT;
			} else if (autoRight.get()) {
				autoPosition = AutoStartingPosition.RIGHT;
			} else if (autoCenter.get()) {
				autoPosition = AutoStartingPosition.CENTER;
			}
		} else {
			autoPosition = Robot.autoPositionChooser.getSelected();
		}
		
		return autoPosition;
	}

	public static AutoMovement getAutoMovement() {
		AutoMovement autoMovement = AutoMovement.SWITCH;
		
		if (Robot.preferences.getBoolean(PreferenceKeys.USE_PHYSICAL_AUTO_CHOOSER, true)) {
			if (autoSwitch.get()) {
				autoMovement = AutoMovement.SWITCH;
			} else if (autoScale.get()) {
				autoMovement = AutoMovement.SCALE;
			} else if (autoBoth.get()) {
				autoMovement = AutoMovement.BOTH;
			} else if (autoForward.get()) {
				autoMovement = AutoMovement.FORWARD;
			} else if (autoNone.get()) {
				autoMovement = AutoMovement.NONE;
			}
		} else {
			autoMovement = Robot.autoMovementChooser.getSelected();
		}
		
		return autoMovement;
	}
}
