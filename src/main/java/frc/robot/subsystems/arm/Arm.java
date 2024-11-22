// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkFlex;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.SoftLimitDirection;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  /** Creates a new Arm. */

  // *** Creating the Arm *** //
  private CANSparkFlex armMotor;
  private RelativeEncoder armEncoder;
  private SparkPIDController armPID;

  public Arm() 
  {

    // Intialize the Motor
    armMotor = new CANSparkFlex(ArmConfig.ID, ArmConfig.motorType);

    // Initialize the Encoder instance
    armEncoder = armMotor.getEncoder();

    // Initialize the PID instance
    armPID = armMotor.getPIDController();

    // PID Configuration
    armPID.setP(ArmConfig.p);
    armPID.setI(ArmConfig.i);
    armPID.setD(ArmConfig.d);

    // Set output range
    armPID.setOutputRange(ArmConfig.outputMin, ArmConfig.outputMax);

    // Set the ramp Rate
    armMotor.setOpenLoopRampRate(ArmConfig.openRampRate);
    armMotor.setClosedLoopRampRate(ArmConfig.closedRampRate);

    // Set the invertsion
    armMotor.setInverted(ArmConfig.kInverted);

    // Set the current limits
    armMotor.setSmartCurrentLimit(ArmConfig.smartCurrentStallLimit, ArmConfig.smartCurrentFreeLimit);

    // Set the position conversion factor
    armEncoder.setPositionConversionFactor(ArmConfig.positionConversionFactor);

    // Set the forward soft limit
    armMotor.enableSoftLimit(SoftLimitDirection.kForward, true); // Set the forward soft limit
    armMotor.setSoftLimit(SoftLimitDirection.kForward, ArmConfig.softLimitFwd); // Set the forward soft limit

    // Set the reverse soft limit
    armMotor.enableSoftLimit(SoftLimitDirection.kReverse, true); // Set the forward soft limit
    armMotor.setSoftLimit(SoftLimitDirection.kReverse, ArmConfig.softLimitRev); // Set the forward soft limit


    // !!!! VERY IMPORTANT !!!! //
    armMotor.burnFlash();

    set(0);
  }

  // *** Getter *** //
  public double get() {
    return armEncoder.getPosition();
  }

  // ??? Setter ??? //
  public void set(double position) {
    armPID.setReference(position, ArmConfig.controlType);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
