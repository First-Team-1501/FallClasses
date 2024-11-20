package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class ShooterConfig {
    
    // CAN IDs
    public static int right_ID = 20;
    public static int left_ID = 21;

    // Motor configs
    public static MotorType motorType = MotorType.kBrushless;
    public static IdleMode idleMode = IdleMode.kCoast;

    // PID Values
    public static double p = 0.01;
    public static double i = 0;
    public static double d = 0;

    // Output
    public static double outputMin = -1;
    public static double outputMax = 1;

    // Ramp rates
    public static double openRampRate = 0.5;
    public static double closedRampRate = 0.5;

    // Inversion
    public static boolean inverted = false;

    // Current Limits
    public static int currentStallLimit = 40;
    public static int currentFreeLimit = 30;

    // Conversion Factors
    public static double velocityConversionFactor = 1;

}
