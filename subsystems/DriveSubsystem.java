// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//we have talking to drive motors, accepting a speed and rotaition

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import frc.robot.Constants;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private CANSparkMax leftDrive = new CANSparkMax(Constants.LEFT_DRIVE_1, MotorType.kBrushless);
  private CANSparkMax leftDriveFollower = new CANSparkMax(Constants.LEFT_DRIVE_2, MotorType.kBrushless);//brushlist is somthing that wont tangle the part thats spinning
  private CANSparkMax rightDrive= new CANSparkMax(Constants.RIGHT_DRIVE_1, MotorType.kBrushless);//each lines says which number (ID) and the type 
  private CANSparkMax rightDriveFollower = new CANSparkMax(Constants.RIGHT_DRIVE_2, MotorType.kBrushless);//17-20//to declare motors as sparkmaxes that are using the CAN bus, can control using PWM
//differential dirve if u change (direcion based on speed drive of diff directions and ur passing in the leader motors on each side L & R leader)
  private DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);//to control motors in bracket
  private RelativeEncoder driveEncoder;
  public DriveSubsystem() {
    //to set type of motor to type of motor, there are also brishless motors(harder to control)
    
    //reset the settings on motor controller
    leftDrive.restoreFactoryDefaults();
    leftDriveFollower.restoreFactoryDefaults();
    rightDrive.restoreFactoryDefaults();
    rightDriveFollower.restoreFactoryDefaults();
    driveEncoder = leftDrive.getEncoder();

    Timer.delay(1);//never put delays (blocking, dont want code to wait try other ways), to wait for the reset to finish
    
    //makes one motor follow another, 2nd left folow 1st left and vise versa
    leftDriveFollower.follow(leftDrive);
    rightDriveFollower.follow(rightDrive);

    //limit the max current through each motor, prevents motors from burning out
    leftDrive.setSmartCurrentLimit(Constants.DRIVE_CURRENT);
    leftDriveFollower.setSmartCurrentLimit(Constants.DRIVE_CURRENT);
    rightDrive.setSmartCurrentLimit(Constants.DRIVE_CURRENT);
    rightDriveFollower.setSmartCurrentLimit(Constants.DRIVE_CURRENT);

    //set the amount of time in secs to reach full voltage, helps prevent browning out
    leftDrive.setOpenLoopRampRate(0.05);//browning out --> drowning out voltage, drops below certain threashold
    rightDrive.setOpenLoopRampRate(0.05);//to prevent take some time(0.05 secs)
    
    leftDrive.setInverted(true);
    driveEncoder.setPosition(0);
    //saves setting above to motor controls
    leftDrive.burnFlash();
    leftDriveFollower.burnFlash();
    rightDrive.burnFlash();
    rightDriveFollower.burnFlash();//sometimes intellisence errors, just build code

    //wait for the settings to be saved
    Timer.delay(1);
    //^for pause
  }//motors are set up above^^

  //we have tank drive forward and backwards
  //split arcade one is for speed and one for direction
  //left is for forward and back right is turns
  public void arcadeDrive(double speed, double rotation){//have to make new differential drive (difference between rotation)
    drive.arcadeDrive(speed, rotation);//control arcade drive set speeds of two sides of drive train
  }
  public double getPosition(){//have to make new differential drive (difference between rotation)
    double position = driveEncoder.getPosition();//control arcade drive set speeds of two sides of drive train
    SmartDashboard.putNumber("position", position);
    return position;
  }

  public void driveMotors(double speed){//have to make new differential drive (difference between rotation)
    if(Math.abs(speed) > 0.4){
      speed = Math.signum(speed)*0.4;
    }
    leftDrive.set(speed);
    rightDrive.set(speed);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
