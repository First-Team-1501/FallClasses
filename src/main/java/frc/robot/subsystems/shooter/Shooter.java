// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkFlex;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */

private CANSparkFlex rightMotor;
private CANSparkFlex leftMotor;

  public Shooter() 
  {

// Initialize motors
rightMotor = new CANSparkFlex(ShooterConfig.right_ID, ShooterConfig.motorType);
leftMotor = new CANSparkFlex(ShooterConfig.left_ID, ShooterConfig.motorType);

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

// Set ramp rate
rightMotor.setOpenLoopRampRate(ShooterConfig.openRampRate);
leftMotor.setOpenLoopRampRate(ShooterConfig.openRampRate);

// Set inversion
rightMotor.setInverted(ShooterConfig.inverted);

// Set current limits
rightMotor.setSmartCurrentLimit(ShooterConfig.currentStalllimit, ShooterConfig.currentFreelimit);
leftMotor.setSmartCurrentLimit(ShooterConfig.currentStalllimit, ShooterConfig.currentFreelimit);

//Make left motor fllow right motor
leftMotor.follow(rightMotor, true);

//Burn Flash -- VERY IMPORTANT
rightMotor.burnFlash();
leftMotor.burnFlash();

  }

  public void set(double speed)
  {
    rightMotor.set(speed);
  }

public void stop()
{
  rightMotor.set(0);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
