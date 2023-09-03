// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int RIGHT_DRIVE_1 = 1;
  public static final int RIGHT_DRIVE_2 = 2;
  public static final int LEFT_DRIVE_1 = 3;
  public static final int LEFT_DRIVE_2 = 4;

  public static final int INTAKE_1 = 5;
  public static final int ARM_1 = 6;
  public static final int ARM_ENCODER = 7;//id should be same so they know which part theyre talking to
  public static final int DRIVE_CURRENT = 40;
  public static final int DRIVER_CONTROLLER = 1;//USB port number in driver station
  public static final double INTAKE_SPEED = 0.4;
  public static final double OUTTAKE_SPEED = -0.4;
  public static final double ARM_P = 0.01;
  public static final double ARM_I = 0;
  public static final double ARM_D = 0;
  public static final double ARM_RATE_TOLERANCE = 10;
  public static final double ARM_ANGLE_TOLERANCE = 10;
  public static final double ARM_UP_ANGLE = 0;
  public static final double ARM_DOWN_ANGLE = 266;
public static final double DRIVE_DISTANCE_TOLERANCE = 5;
public static final double DRIVE_RATE_TOLERANCE = 0.2;
  public static final double DRIVE_P = 0.01;
  public static final double DRIVE_I = 0;
  public static final double DRIVE_D = 0;
}
