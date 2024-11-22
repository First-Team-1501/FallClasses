package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ArmConfig {

    // Canbus ID
    public static int ID = 22;

    // Motor type
    public static MotorType motorType = MotorType.kBrushed;

    // PID values
    public static double p = 0.1;
    public static double i = 0;
    public static double d = 0;

    // Idle mode
    public static IdleMode idleMode = IdleMode.kBrake;

    // Min + max outputs
    public static double outputmin = -1;
    public static double outputmax = 1;

    // Ramp rate
    public static double openRampRate = 0.25;
    public static double closedRampRate = 0.25;

    // Inversion 
    public static boolean invereted = false;

    // Current limits
    public static int smartCurrentStallLimit = 40;
    public static int smartCurrentfreeLimit = 30;

    // Conversion factor
    public static double positionConversionFactor = 1;

    // Soft limits
    public static float softLimitFwd = 15;
    public static float softLimitRev = -15;

    //Control type
    public static ControlType controlType = ControlType.kPosition;
     
    

        


}
