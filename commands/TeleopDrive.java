// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class TeleopDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem drive;
  private final DoubleSupplier speed;
  private final DoubleSupplier rotation;
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public TeleopDrive(DriveSubsystem _drive, DoubleSupplier _speed, DoubleSupplier _rotation) {//pass in drive subsystem
    drive = _drive;
    speed = _speed;
    rotation = _rotation;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);//cant have two things controlling driver at the same time
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double spd=speed.getAsDouble();
    double rot=rotation.getAsDouble();

    drive.arcadeDrive(Math.signum(spd)*spd*spd, Math.signum(rot)*rot*rot);//this is to not make it a fixed number //to square it +give exponential for more control
  }//telop drive command to pass in numbers from joysticks

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
