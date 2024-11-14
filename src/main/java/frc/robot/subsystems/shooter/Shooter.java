// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import java.util.Map;

import com.revrobotics.CANSparkFlex;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  
  // Create the motors
  private CANSparkFlex leftMotor;
  private CANSparkFlex rightMotor;

  GenericEntry shooterRPM;
  
  /** Creates a new Shooter. */
  public Shooter() 
  {
    // Initialize motors
    leftMotor = new CANSparkFlex(ShooterConfig.left_ID, ShooterConfig.left_motorType);
    rightMotor = new CANSparkFlex(ShooterConfig.right_ID, ShooterConfig.right_motorType);

    // Set the idle mode
    leftMotor.setIdleMode(ShooterConfig.left_idleMode);
    rightMotor.setIdleMode(ShooterConfig.right_idleMode);

    // PID Configuration
    leftMotor.getPIDController().setP(ShooterConfig.left_p);
    leftMotor.getPIDController().setI(ShooterConfig.left_i);
    leftMotor.getPIDController().setD(ShooterConfig.left_d);
    leftMotor.getPIDController().setIZone(ShooterConfig.left_IZone);
    leftMotor.getPIDController().setDFilter(ShooterConfig.left_DFilter);

    rightMotor.getPIDController().setP(ShooterConfig.right_p);
    rightMotor.getPIDController().setI(ShooterConfig.right_i);
    rightMotor.getPIDController().setD(ShooterConfig.right_d);
    rightMotor.getPIDController().setIZone(ShooterConfig.right_IZone);
    rightMotor.getPIDController().setDFilter(ShooterConfig.right_DFilter);

    // Set output range
    leftMotor.getPIDController().setOutputRange(ShooterConfig.left_outputMin, ShooterConfig.left_outputMax);
    rightMotor.getPIDController().setOutputRange(ShooterConfig.right_outputMin, ShooterConfig.right_outputMax);

    // Set ramp rate
    leftMotor.setOpenLoopRampRate(ShooterConfig.left_openRampRate);
    rightMotor.setOpenLoopRampRate(ShooterConfig.right_openRampRate);

    // Set inversion
    rightMotor.setInverted(ShooterConfig.right_kInverted);

    // Set current limits
    leftMotor.setSmartCurrentLimit(ShooterConfig.left_smartCurrentStallLimit, ShooterConfig.left_smartCurrentFreeLimit);
    rightMotor.setSmartCurrentLimit(ShooterConfig.right_smartCurrentStallLimit,
        ShooterConfig.right_smartCurrentFreeLimit);

    // Left motor follows right
    leftMotor.follow(rightMotor, true); // TRUE = INVERTED, VERY IMPORTANT

    // Burn flash
    leftMotor.burnFlash();
    rightMotor.burnFlash();

    //Creates the shuffleboardInit
    shuffleBoardInit();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateShuffleboard();
  }

  // Stop Motors
  public void stop()
  {
    rightMotor.set(0);
  }

  // Set the speed of the left and the right motors
  public void set(double speed)
  {
    rightMotor.set(speed);
  }

  public double getRPM()
  {
    return rightMotor.getEncoder().getVelocity();
  }

  public void shuffleBoardInit()
  {

    shooterRPM = Shuffleboard.getTab("Info")
      .add("ShooterRPM", getRPM())
      .withWidget(BuiltInWidgets.kDial)
      .withProperties(Map.of("min", 0, "max", 5750))
      .withPosition(4, 4)
      .withSize(3, 3)
      .getEntry();
  }

  public void updateShuffleboard()
  {
    shooterRPM.setDouble(getRPM());
  }

}
