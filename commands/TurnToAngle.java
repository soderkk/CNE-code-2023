package frc.robot.commands;//path

import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ArmSubsystemPID;
import frc.robot.Constants;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;

/** A command that will turn the robot to the specified angle. */
public class TurnToAngle extends PIDCommand {
  /**
   * Turns to robot to the specified angle.
   *
   * @param targetAngleDegrees The angle to turn to
   * @param drive The drive subsystem to use
   */
  public TurnToAngle(double targetAngleDegrees, ArmSubsystemPID arm) {
    super(
        new PIDController(Constants.ARM_P, Constants.ARM_I, Constants.ARM_D),
        // Close loop on heading
        arm::getPosition,
        // Set reference to target
        targetAngleDegrees, 
        // Pipe output to turn robot
        output -> arm.set(output),
        // Require the drive
        arm);

    // Set the controller to be continuous (because it is an angle controller)
    //getController().enableContinuousInput(0, 360);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(Constants.ARM_ANGLE_TOLERANCE, Constants.ARM_RATE_TOLERANCE);
  }

  @Override
  public boolean isFinished() {
    // End when the controller is at the reference.
    return getController().atSetpoint();
  }
}