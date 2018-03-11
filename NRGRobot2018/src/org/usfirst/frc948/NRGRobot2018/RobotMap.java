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

import org.usfirst.frc948.NRGRobot2018.utilities.Arduino;
import org.usfirst.frc948.NRGRobot2018.utilities.ContinuousGyro;
import org.usfirst.frc948.NRGRobot2018.vision.I2Cwrapper;
import org.usfirst.frc948.NRGRobot2018.vision.IPixyLink;
import org.usfirst.frc948.NRGRobot2018.vision.PixyCam;
import org.usfirst.frc948.NRGRobot2018.vision.SPIwrapper;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into to a variable name.
 * This provides flexibility changing wiring, makes checking the wiring easier and significantly
 * reduces the number of magic numbers floating around.
 */

public class RobotMap {
	// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	public static Victor driveLeftFrontMotor;
	public static Victor driveLeftRearMotor;
	public static Victor driveRightFrontMotor;
	public static Victor driveRightRearMotor;
	public static MecanumDrive driveMecanumDrive;

	public static Victor cubeLifterMotor;
	
	public static Victor acquirerRightMotor;
	public static Victor acquirerLeftMotor;
	public static Victor cubeTitlerMotor;

	public static Victor climberMotor;

	public static Encoder xEncoder;
	public static Encoder yEncoder;
	public static Encoder cubeLiftEncoder;
	public static Encoder cubeTiltEncoder;
	
	public static DigitalInput lifterLowerLimitSwitch;
	public static DigitalInput lifterUpperLimitSwitch;
	public static DigitalInput cubeDetectSwitch;

	public static AHRS navx;
	public static ContinuousGyro gyro;
	
	public static IPixyLink pixyLink;
	public static PixyCam pixy;
	
	public static I2Cwrapper arduinoLink;
	public static Arduino arduino;
	
	public static void init() {
		// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		driveLeftFrontMotor = new Victor(1);
		driveLeftRearMotor = new Victor(3);
		driveRightFrontMotor = new Victor(0);
		driveRightRearMotor = new Victor(2);
		
		driveMecanumDrive = new MecanumDrive(driveLeftFrontMotor, driveLeftRearMotor, driveRightFrontMotor,
				driveRightRearMotor);
		driveMecanumDrive.setSafetyEnabled(false);
		driveMecanumDrive.setExpiration(0.1);
		driveMecanumDrive.setMaxOutput(1.0);

		cubeLifterMotor = new Victor(4);
		
		acquirerLeftMotor = new Victor(5);
		acquirerRightMotor = new Victor(6);
		acquirerRightMotor.setInverted(true);
		
		cubeTitlerMotor = new Victor(7);
		cubeTitlerMotor.setInverted(false);
		
		climberMotor = new Victor(8);

		xEncoder = new Encoder(2, 3, false); // positive is right
		yEncoder = new Encoder(0, 1, true); // positive is forward
		cubeLiftEncoder = new Encoder(6, 7, false);
		cubeTiltEncoder = new Encoder(8, 9, false);
		
		xEncoder.setDistancePerPulse(0.0478); // inches per pulse, encoder is slipping
		yEncoder.setDistancePerPulse(0.0498);
		cubeLiftEncoder.setDistancePerPulse(1.0); 
		cubeTiltEncoder.setDistancePerPulse(1.0);

		navx = new AHRS(SPI.Port.kMXP);
		gyro = new ContinuousGyro(navx);
		
		lifterLowerLimitSwitch = new DigitalInput(4);
		lifterUpperLimitSwitch = new DigitalInput(5);
		cubeDetectSwitch = new DigitalInput(10);
		
		pixyLink = new SPIwrapper(SPI.Port.kOnboardCS0);
		pixy = new PixyCam(pixyLink);
		pixy.startVisionThread();
		
		arduinoLink = new I2Cwrapper(I2C.Port.kOnboard, 84);
		arduino = new Arduino(arduinoLink);
	}
}
