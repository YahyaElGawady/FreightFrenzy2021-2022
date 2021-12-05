package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.internal.RobotComponent;
import org.firstinspires.ftc.robotcontroller.internal.RobotBase;

import java.util.ArrayList;


@SuppressWarnings("MoveFieldAssignmentToInitializer")
public class Drivetrain extends RobotComponent {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor[] motors = new DcMotor[4];

    public ModernRoboticsI2cGyro gyroSensor;
    public ModernRoboticsI2cRangeSensor frontRange = null;

    public final double STRAIGHT = 0;

    public static final double amountError = 2;
    public static final double     COUNTS_PER_MOTOR_REV    = 753.2;
    public static final double     DRIVE_GEAR_REDUCTION    = 1.0;
    public static final double     WHEEL_DIAMETER_INCHES   = /*3.77953*/3.75;
    public static final double     COUNTS_PER_INCH  = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
            / (WHEEL_DIAMETER_INCHES * 3.14159265358979323846264);
    public static final double DRIVE_SPEED = 0.65;     // Nominal speed for better accuracy.
    public static final double TURN_SPEED = 0.5;     // Nominal half speed for better accuracy.

    public Drivetrain(RobotBase BASE) {
        super(BASE);
        initMotors();
    }

     void initMotors() {

               frontLeft = base().getMapper().mapMotor("frontLeft", DcMotorSimple.Direction.REVERSE);
               motors[0] = frontLeft;

               backLeft = base().getMapper().mapMotor("backLeft", DcMotorSimple.Direction.REVERSE);
               motors[1] = backLeft;

               frontRight = base().getMapper().mapMotor("frontRight", DcMotorSimple.Direction.REVERSE);
               motors[2] = frontRight;

         backRight = base().getMapper().mapMotor("backRight", DcMotorSimple.Direction.REVERSE);
        motors[3] = backRight;

        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

     }
    public void moveInches(double speed,
        double frontLeftInches, double frontRightInches, double backLeftInches,
        double backRightInches)
    {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;
        boolean goodEnough = false;
        double ErrorAmount;
        // Ensure that the opmode is still active
        if (base.getOpMode().opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = frontLeft.getCurrentPosition() + (int) (frontLeftInches * COUNTS_PER_INCH);
            newFrontRightTarget = frontRight.getCurrentPosition() + (int) (frontRightInches * COUNTS_PER_INCH);
            newBackLeftTarget = backLeft.getCurrentPosition() + (int) (backLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = backRight.getCurrentPosition() + (int) (backRightInches * COUNTS_PER_INCH);

            setTargetPositions(newFrontLeftTarget, newFrontRightTarget,
                    newBackLeftTarget, newBackRightTarget);

            // Turn On RUN_TO_POSITION
            setModes(DcMotor.RunMode.RUN_TO_POSITION);

            setPowers(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (base().getOpMode().opModeIsActive() &&
                    frontLeft.isBusy() && frontRight.isBusy() &&
                    backLeft.isBusy() && backRight.isBusy() && !goodEnough) {

                // Display it for the driver.
                base().getTelemetry().addData("Path1", "Running to %7d :%7d", newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
                base().getTelemetry().addData("Path2", "Running at %7d :%7d",

                        frontLeft.getCurrentPosition(),
                        frontRight.getCurrentPosition(),
                        backLeft.getCurrentPosition(),
                        backRight.getCurrentPosition());
                base().getTelemetry().addData("frontLeft", frontLeft.getCurrentPosition());
                base().getTelemetry().addData("backLeft", backLeft.getCurrentPosition());
                base().getTelemetry().addData("frontRight", frontRight.getCurrentPosition());
                base().getTelemetry().addData("backright", backRight.getCurrentPosition());

                base().getTelemetry().update();

                ErrorAmount = ((Math.abs(((newBackLeftTarget) - (backLeft.getCurrentPosition())))
                        + (Math.abs(((newFrontLeftTarget) - (frontLeft.getCurrentPosition()))))
                        + (Math.abs((newBackRightTarget) - (backRight.getCurrentPosition())))
                        + (Math.abs(((newFrontRightTarget) - (frontRight.getCurrentPosition()))))) / COUNTS_PER_INCH);
                if (ErrorAmount < amountError) {
                    goodEnough = true;
                }
            }

            // Stop all motion;

            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);

            // Turn off RUN_TO_POSITION
            frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void drive(double forward, double turn, boolean slowMode) {

        forward = getProcessedInput(forward);
        turn = getProcessedInput(turn);

        double leftFrontPower = forward + turn;
        double leftBackPower = forward + turn;
        double rightFrontPower = forward - turn;
        double rightBackPower = forward - turn;
        double[] powers = {leftFrontPower, leftBackPower, rightFrontPower, rightBackPower};

        boolean needToScale = false;
        for (double power : powers) {
            if (Math.abs(power) > 1) {
                needToScale = true;
                break;
            }
        }
        if (needToScale) {
            double greatest = 0;
            for (double power : powers) {
                if (Math.abs(power) > greatest) {
                    greatest = Math.abs(power);
                }
            }
            powers[0] = leftFrontPower / greatest;
            powers[1] = leftBackPower / greatest;
            powers[2] = rightFrontPower / greatest;
            powers[3] = rightBackPower / greatest;

        }
        if(slowMode) {
            for (int i = 0; i <= 3; i++) {
                powers[i] /= 2;
            }
        }
        setPowers(powers);
    }
        public double[] getPowers () {
            double[] powers = {frontLeft.getPower(), backLeft.getPower(), frontRight.getPower(), backRight.getPower()};
            return powers;
        }

        public void setPowers ( double[] powers){
            frontLeft.setPower(powers[0]);
            backLeft.setPower(powers[1]);
            frontRight.setPower(powers[2]);
            backRight.setPower(powers[3]);
        }
        public void setPowers ( double power){
            for (DcMotor m : motors) {
                m.setPower(power);
            }
        }

        public double getAverageEncoders (ArrayList<DcMotor> dcmotors) {
            double sum = 0;
            for (DcMotor m : dcmotors) {
                sum += Math.abs(m.getCurrentPosition());
            }
            return (sum / (double) (dcmotors.size()));
        }

        public void setTargetPositions(int fl, int fr, int bl, int br) {
            frontLeft.setTargetPosition(fl);
            frontRight.setTargetPosition(fr);
            backLeft.setTargetPosition(bl);
            backRight.setTargetPosition(br);
        }
        public void stop () {
            setPowers(0);
        }

        public void setModes (DcMotor.RunMode runMode){
            for (DcMotor motor : motors) {
                motor.setMode(runMode);
            }
        }
        public void setZeroPowerBehaviors (DcMotor.ZeroPowerBehavior behavior){
            for (DcMotor motor : motors) {
                motor.setZeroPowerBehavior(behavior);
            }
        }

        private double getProcessedInput ( double hardInput){
            hardInput = Range.clip(hardInput, -1, 1);
            hardInput = Math.pow(hardInput, 3);
            return hardInput;
        }
}
