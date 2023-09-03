package frc.robot.commands;//path

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ArmSubsystemPID;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Constants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

public class DriveStraight extends PIDCommand {
  private final DriveSubsystem drive;

  /**
   * Turns to robot to the specified angle.
   *
  
   * @param drive The drive subsystem to use
   */
  public DriveStraight(DriveSubsystem _drive) {
    super(
        new PIDController(Constants.DRIVE_P, Constants.DRIVE_I, Constants.DRIVE_D),
        // Close loop on heading
        _drive::getPosition,
        // Set reference to target
        100, 
        // Pipe output to turn robot
        output -> _drive.driveMotors(output),
        // Require the drive
        _drive);

        drive = _drive;

    // Set the controller to be continuous (because it is an angle controller)
    //getController().enableContinuousInput(0, 360);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(Constants.DRIVE_DISTANCE_TOLERANCE, Constants.DRIVE_RATE_TOLERANCE);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
  @Override
  public void end(boolean interrupted) { 
    drive.driveMotors(0);
  }
}