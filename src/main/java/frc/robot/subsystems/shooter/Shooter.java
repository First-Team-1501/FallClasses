// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkFlex;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */

  // Creating the motors
  private CANSparkFlex rightMotor;
  private CANSparkFlex leftMotor;

  public Shooter() 
  {

    // Initializing the motors
    rightMotor = new CANSparkFlex(ShooterConfig.right_ID, ShooterConfig.motorType);
    leftMotor = new CANSparkFlex(ShooterConfig.left_ID, ShooterConfig.motorType);

    // Setting the idle mode
    rightMotor.setIdleMode(ShooterConfig.idleMode);
    leftMotor.setIdleMode(ShooterConfig.idleMode);

    // Set idle mode
    rightMotor.setIdleMode(ShooterConfig.idleMode);
    leftMotor.setIdleMode(ShooterConfig.idleMode);

    // PID Config
    rightMotor.getPIDController().setP(ShooterConfig.p);
    rightMotor.getPIDController().setI(ShooterConfig.i);
    rightMotor.getPIDController().setD(ShooterConfig.d);

    leftMotor.getPIDController().setP(ShooterConfig.p);
    leftMotor.getPIDController().setI(ShooterConfig.i);
    leftMotor.getPIDController().setD(ShooterConfig.d);
    
    // Set output range
    rightMotor.getPIDController().setOutputRange(ShooterConfig.outputMin, ShooterConfig.outputMax);
    leftMotor.getPIDController().setOutputRange(ShooterConfig.outputMin, ShooterConfig.outputMax);

    // Set ramp rates
    rightMotor.setOpenLoopRampRate(ShooterConfig.openRampRate);
    leftMotor.setOpenLoopRampRate(ShooterConfig.openRampRate);

    // Set inversion
    rightMotor.setInverted(ShooterConfig.inverted);

    // Set Current limits
    rightMotor.setSmartCurrentLimit(ShooterConfig.currentStaleLimit, ShooterConfig.currentFreeLimit);
    leftMotor.setSmartCurrentLimit(ShooterConfig.currentStaleLimit, ShooterConfig.currentFreeLimit);

    // Make left motor follow right motor
    leftMotor.follow(rightMotor, true);

    // BURN Flash -- VERY IMPORTANT
    rightMotor.burnFlash();
    leftMotor.burnFlash();



  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
