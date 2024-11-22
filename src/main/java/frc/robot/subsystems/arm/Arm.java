// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.SoftLimitDirection;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
 
private CANSparkFlex armMotor;
private RelativeEncoder armEncoder;
private SparkPIDController armPID;

  public Arm() 
  {

// initialize motor
armMotor = new CANSparkFlex(ArmConfig.ID, ArmConfig.motorType);
armEncoder = armMotor.getEncoder();
armPID = armMotor.getPIDController();


// PID Config
armPID.setP(ArmConfig.p);
armPID.setI(ArmConfig.i);
armPID.setD(ArmConfig.d);

//  Set output range
armPID.setOutputRange(ArmConfig.outputMin, ArmConfig.outputMax);

// Set ramp rate
armMotor.setOpenLoopRampRate(ArmConfig.openRampRate);
armMotor.setClosedLoopRampRate(ArmConfig.closedRampRate);

// Set Inversion
armMotor.setInverted(ArmConfig.inverted);

// Set Current Limits
armMotor.setSmartCurrentLimit(ArmConfig.smartCurrentStallLimit, ArmConfig.smartCurrentFreeLimit);

// Set Position Conversion Factor
armEncoder.setPositionConversionFactor(ArmConfig.positionConversionFactor);

// Set Forward Soft Limit
armMotor.enableSoftLimit(SoftLimitDirection.kForward, true);
armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConfig.softLimitForward);

//  Set Reverse Soft Limit
armMotor.enableSoftLimit(SoftLimitDirection.kReverse, true);
armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConfig.softLimitReverse);

//  Burn Flash
armMotor.burnFlash();

set(0);

  }

// Get position
public double get()
{
  return armEncoder.getPosition();
}

// Set position
public void set(double position)
{
  armPID.setReference(position, ArmConfig.controlType);
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
