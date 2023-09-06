package frc.robot.commands;//path

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

//not working yet
public class Balance extends CommandBase {
  private final DriveSubsystem drive;
    private int state;
    private int debounceCount;
    private double robotSpeedSlow;
    private double robotSpeedFast;
    private double onChargeStationDegree;
    private double levelDegree;
    private double debounceTime;


  /**
   * Turns to robot to the specified angle.
   *
  
   * @param drive The drive subsystem to use
   */
  public Balance(DriveSubsystem _drive) {       
    drive = _drive;
    addRequirements(drive);//cant have two things controlling driver at the same time

    state = 0;
        debounceCount = 0;

        /**********
         * CONFIG *
         **********/
        // Speed the robot drived while scoring/approaching station, default = 0.4
        robotSpeedFast = 0.4;

        // Speed the robot drives while balancing itself on the charge station.
        // Should be roughly half the fast speed, to make the robot more accurate,
        // default = 0.2
        robotSpeedSlow = 0.2;

        // Angle where the robot knows it is on the charge station, default = 13.0
        onChargeStationDegree = 13.0;

        // Angle where the robot can assume it is level on the charging station
        // Used for exiting the drive forward sequence as well as for auto balancing,
        // default = 6.0
        levelDegree = 6.0;

        // Amount of time a sensor condition needs to be met before changing states in
        // seconds
        // Reduces the impact of sensor noice, but too high can make the auto run
        // slower, default = 0.2
        debounceTime = 0.2;

  }

  @Override
  public void execute(){
    double speed = autoBalanceRoutine();
    drive.driveMotors2(speed); 
  }

  // routine for automatically driving onto and engaging the charge station.
    // returns a value from -1.0 to 1.0, which left and right motors should be set
    // to.
    public double autoBalanceRoutine() {
      switch (state) {
          // drive forwards to approach station, exit when tilt is detected
          case 0:
              if (drive.getTilt() > onChargeStationDegree) {
                  debounceCount++;
              }
              if (debounceCount > drive.secondsToTicks(debounceTime)) {
                  state = 1;
                  debounceCount = 0;
                  return robotSpeedSlow;
              }
              return robotSpeedFast;
          // driving up charge station, drive slower, stopping when level
          case 1:
              if (drive.getTilt() < levelDegree) {
                  debounceCount++;
              }
              if (debounceCount > drive.secondsToTicks(debounceTime)) {
                  state = 2;
                  debounceCount = 0;
                  return 0;
              }
              return robotSpeedSlow;
          // on charge station, stop motors and wait for end of auto
          case 2:
              if (Math.abs(drive.getTilt()) <= levelDegree / 2) {
                  debounceCount++;
              }
              if (debounceCount > drive.secondsToTicks(debounceTime)) {
                  state = 4;
                  debounceCount = 0;
                  return 0;
              }
              if (drive.getTilt() >= levelDegree) {
                  return 0.1;
              } else if (drive.getTilt() <= -levelDegree) {
                  return -0.1;
              }
          case 3:
              return 0;
      }
      return 0;
  }

  @Override
  public void end(boolean interrupted) { 
    //drive.driveMotors(0);
  }
}