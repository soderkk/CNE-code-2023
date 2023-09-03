// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import frc.robot.commands.ArmDown;
import frc.robot.commands.ArmHold;
import frc.robot.commands.ArmUp;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.Outtake;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public final class Autos {
  /** Example static factory for an autonomous command. */
  public static CommandBase doNothing(DriveSubsystem drive) {
    return Commands.sequence();
  }
  public static CommandBase scoreDrive(DriveSubsystem drive, ArmSubsystem arm, IntakeSubsystem intake) {
  
    return Commands.sequence(
      new ParallelDeadlineGroup(
        new WaitCommand(1),
        new ArmUp(arm)
        ),
        new ParallelDeadlineGroup(
        new WaitCommand(1),
        new Outtake(intake)
        ),
        new InstantCommand(
          () -> intake.spin(0),intake
        ),
        new ParallelDeadlineGroup(
        new WaitCommand(1),
        new ArmDown(arm)
        ), 

        new ParallelCommandGroup(
          new ArmHold(arm),
          new DriveStraight(drive)
        )
    );
  }
  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
