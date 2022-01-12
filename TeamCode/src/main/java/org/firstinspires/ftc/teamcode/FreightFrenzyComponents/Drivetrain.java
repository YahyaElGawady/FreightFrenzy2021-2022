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
//    public ModernRoboticsI2cRangeSensor frontRange = null;

    public final double STRAIGHT = 0;

    public static final double amountError = 2;
    public static final double     COUNTS_PER_MOTOR_REV    = 753.2;
    public static final double     DRIVE_GEAR_REDUCTION    = 1.0;
    public static final double     WHEEL_DIAMETER_INCHES   = /*3.77953*/3.75;
    public static final double     COUNTS_PER_INCH  = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION)
            / (WHEEL_DIAMETER_INCHES * 3.14159265358979323846264);
    public static final double DRIVE_SPEED = 0.65;     // Nominal speed for better accuracy.
    public static final double TURN_SPEED = 0.5;     // Nominal half speed for better accuracy.
    static final double HEADING_THRESHOLD = 1;      // As tight as we can make it with an integer gyro
    static final double P_TURN_COEFF = 0.1;     // Larger is more responsive, but also less stable
    static final double P_DRIVE_COEFF = 0.07;
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

               gyroSensor = base().getMapper().mapMRGyro("gyro");
               gyroSensor.calibrate();
               while(gyroSensor.isCalibrating());
               base().getTelemetry().addLine("Gyro Calibrated");
               base().getTelemetry().update();
        setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setModes(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);

     }
    public void moveEncoders(double speed,
                           int frontLeftEncoders, int frontRightEncoders, int backLeftEncoders,
                           int backRightEncoders)
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
            newFrontLeftTarget = frontLeft.getCurrentPosition() + frontLeftEncoders;
            newFrontRightTarget = frontRight.getCurrentPosition() + frontRightEncoders;
            newBackLeftTarget = backLeft.getCurrentPosition() + backLeftEncoders;
            newBackRightTarget = backRight.getCurrentPosition() + backRightEncoders;

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
//                base().getTelemetry().addData("Path1", "Running to %7d :%7d", newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
//                base().getTelemetry().addData("Path2", "Running at %7d :%7d",
//
//                        frontLeft.getCurrentPosition(),
//                        frontRight.getCurrentPosition(),
//                        backLeft.getCurrentPosition(),
//                        backRight.getCurrentPosition());
//                base().getTelemetry().addData("frontLeft", frontLeft.getCurrentPosition());
//                base().getTelemetry().addData("backLeft", backLeft.getCurrentPosition());
//                base().getTelemetry().addData("frontRight", frontRight.getCurrentPosition());
//                base().getTelemetry().addData("backright", backRight.getCurrentPosition());

//                base().getTelemetry().update();

                ErrorAmount = ((Math.abs(((newBackLeftTarget) - (backLeft.getCurrentPosition())))
                        + (Math.abs(((newFrontLeftTarget) - (frontLeft.getCurrentPosition()))))
                        + (Math.abs((newBackRightTarget) - (backRight.getCurrentPosition())))
                        + (Math.abs(((newFrontRightTarget) - (frontRight.getCurrentPosition()))))) / COUNTS_PER_INCH);
                if (ErrorAmount < amountError) {
                    goodEnough = true;
                }
            }

            // Stop all motion;

            setPowers(0);

            // Turn off RUN_TO_POSITION
            setModes(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void moveInches(double speed,
        double frontLeftInches, double frontRightInches, double backLeftInches,
        double backRightInches)
    {
        moveEncoders(speed,
                (int)(frontLeftInches * COUNTS_PER_INCH),
                (int)(frontRightInches * COUNTS_PER_INCH),
                (int)(backLeftInches * COUNTS_PER_INCH),
                (int)(backRightInches * COUNTS_PER_INCH));
//        int newFrontLeftTarget;
//        int newFrontRightTarget;
//        int newBackLeftTarget;
//        int newBackRightTarget;
//        boolean goodEnough = false;
//        double ErrorAmount;
//        // Ensure that the opmode is still active
//        if (base.getOpMode().opModeIsActive()) {
//
//            // Determine new target position, and pass to motor controller
//            newFrontLeftTarget = frontLeft.getCurrentPosition() + (int) (frontLeftInches * COUNTS_PER_INCH);
//            newFrontRightTarget = frontRight.getCurrentPosition() + (int) (frontRightInches * COUNTS_PER_INCH);
//            newBackLeftTarget = backLeft.getCurrentPosition() + (int) (backLeftInches * COUNTS_PER_INCH);
//            newBackRightTarget = backRight.getCurrentPosition() + (int) (backRightInches * COUNTS_PER_INCH);
//
//            setTargetPositions(newFrontLeftTarget, newFrontRightTarget,
//                    newBackLeftTarget, newBackRightTarget);
//
//            // Turn On RUN_TO_POSITION
//            setModes(DcMotor.RunMode.RUN_TO_POSITION);
//
//            setPowers(Math.abs(speed));
//
//            // keep looping while we are still active, and there is time left, and both motors are running.
//            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
//            // its target position, the motion will stop.  This is "safer" in the event that the robot will
//            // always end the motion as soon as possible.
//            // However, if you require that BOTH motors have finished their moves before the robot continues
//            // onto the next step, use (isBusy() || isBusy()) in the loop test.
//            while (base().getOpMode().opModeIsActive() &&
//                    frontLeft.isBusy() && frontRight.isBusy() &&
//                    backLeft.isBusy() && backRight.isBusy() && !goodEnough) {
//
//                // Display it for the driver.
//                base().getTelemetry().addData("Path1", "Running to %7d :%7d", newFrontLeftTarget, newBackLeftTarget, newFrontRightTarget, newBackRightTarget);
//                base().getTelemetry().addData("Path2", "Running at %7d :%7d",
//
//                        frontLeft.getCurrentPosition(),
//                        frontRight.getCurrentPosition(),
//                        backLeft.getCurrentPosition(),
//                        backRight.getCurrentPosition());
//                base().getTelemetry().addData("frontLeft", frontLeft.getCurrentPosition());
//                base().getTelemetry().addData("backLeft", backLeft.getCurrentPosition());
//                base().getTelemetry().addData("frontRight", frontRight.getCurrentPosition());
//                base().getTelemetry().addData("backright", backRight.getCurrentPosition());
//
//                base().getTelemetry().update();
//
//                ErrorAmount = ((Math.abs(((newBackLeftTarget) - (backLeft.getCurrentPosition())))
//                        + (Math.abs(((newFrontLeftTarget) - (frontLeft.getCurrentPosition()))))
//                        + (Math.abs((newBackRightTarget) - (backRight.getCurrentPosition())))
//                        + (Math.abs(((newFrontRightTarget) - (frontRight.getCurrentPosition()))))) / COUNTS_PER_INCH);
//                if (ErrorAmount < amountError) {
//                    goodEnough = true;
//                }
//            }
//
//            // Stop all motion;
//
//            setPowers(0);
//
//            // Turn off RUN_TO_POSITION
//            setModes(DcMotor.RunMode.RUN_USING_ENCODER);
//        }
    }
    public double getError ( double targetAngle){

        double robotError;

        // calculate error in -179 to +180 range  (
        robotError = targetAngle - gyroSensor.getHeading();
        while (robotError > 180) robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }
    public double getSteer ( double error, double PCoeff){
        return Range.clip(error * PCoeff, -DRIVE_SPEED, 1);
    }
    public void gyroTurn ( double speed, double angle){

        // keep looping while we are still active, and not on heading.
        while (base().getOpMode().opModeIsActive() && !onHeading(speed, angle, P_TURN_COEFF)) {
            // Update telemetry & Allow time for other processes to run.
            base().getTelemetry().update();
        }
    }
    boolean onHeading ( double speed, double angle, double PCoeff){
        double error;
        double steer;
        boolean onTarget = false;
        double leftSpeed;
        double rightSpeed;

        // determine turn power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        } else {
            steer = getSteer(error, PCoeff);
            rightSpeed = speed * steer;
            leftSpeed = -rightSpeed;
        }

        // Send desired speeds to motors.
        frontLeft.setPower(leftSpeed);
        backLeft.setPower(leftSpeed);
        backRight.setPower(rightSpeed);
        frontRight.setPower(rightSpeed);

        // Display it for the driver.
        base().getTelemetry().addData("Target", "%5.2f", angle);
        base().getTelemetry().addData("Err/St", "%5.2f/%5.2f", error, steer);
        base().getTelemetry().addData("Speed.", "%5.2f:%5.2f", leftSpeed, rightSpeed);

        return onTarget;
    }
    public void gyroDrive ( double speed,
                            double frontLeftInches, double frontRightInches, double backLeftInches,
                            double backRightInches,
                            double angle, double timeoutS){

        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        double HalfMaxOne;
        double HalfMaxTwo;

        double max;

        double error;
        double steer;
        double frontLeftSpeed;
        double frontRightSpeed;
        double backLeftSpeed;
        double backRightSpeed;

        double ErrorAmount;
        boolean goodEnough = false;

        // Ensure that the opmode is still active
        if (base().getOpMode().opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = frontLeft.getCurrentPosition() + (int) (frontLeftInches * COUNTS_PER_INCH);
            newFrontRightTarget = frontRight.getCurrentPosition() + (int) (frontRightInches * COUNTS_PER_INCH);
            newBackLeftTarget = backLeft.getCurrentPosition() + (int) (backLeftInches * COUNTS_PER_INCH);
            newBackRightTarget = backRight.getCurrentPosition() + (int) (backRightInches * COUNTS_PER_INCH);


            // Set Target and Turn On RUN_TO_POSITION
            frontLeft.setTargetPosition(newFrontLeftTarget);
            frontRight.setTargetPosition(newFrontRightTarget);
            backLeft.setTargetPosition(newBackLeftTarget);
            backRight.setTargetPosition(newBackRightTarget);

            setModes(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            setPowers(Math.abs(speed));
            // keep looping while we are still active, and BOTH motors are running.
            while (base.getOpMode().opModeIsActive() &&
                    ((frontLeft.isBusy() && frontRight.isBusy()) && (backLeft.isBusy() && backRight.isBusy())) && !goodEnough) {


                // adjust relative speed based on heading error.
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                // if driving in reverse, the motor correction also needs to be reversed
                if (frontLeftInches < 0 && frontRightInches < 0 && backLeftInches < 0 && backRightInches < 0)
                    steer *= -1.0;

                frontLeftSpeed = speed - steer;
                backLeftSpeed = speed - steer;
                backRightSpeed = speed + steer;
                frontRightSpeed = speed + steer;

                // Normalize speeds if either one exceeds +/- 1.0;
                HalfMaxOne = Math.max(Math.abs(frontLeftSpeed), Math.abs(backLeftSpeed));
                HalfMaxTwo = Math.max(Math.abs(frontRightSpeed), Math.abs(backRightSpeed));
                max = Math.max(Math.abs(HalfMaxOne), Math.abs(HalfMaxTwo));
                if (max > 1.0) {
                    frontLeftSpeed /= max;
                    frontRightSpeed /= max;
                    backLeftSpeed /= max;
                    backRightSpeed /= max;
                }

                frontLeft.setPower(frontLeftSpeed);
                frontRight.setPower(frontRightSpeed);
                backLeft.setPower(backLeftSpeed);
                backRight.setPower(backRightSpeed);

                // Display drive status for the driver.
                base().getTelemetry().addData("Err/St", "%5.1f/%5.1f", error, steer);
                base().getTelemetry().addData("Target", "%7d:%7d", newBackLeftTarget, newBackRightTarget, newFrontLeftTarget, newFrontRightTarget);
                base().getTelemetry().addData("Actual", "%7d:%7d", backLeft.getCurrentPosition(), backRight.getCurrentPosition(), frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());
                base().getTelemetry().addData("Speed", "%5.2f:%5.2f", backLeftSpeed, backRightSpeed, frontLeftSpeed, frontRightSpeed);
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
            setPowers(0);

            // Turn off RUN_TO_POSITION
            setModes(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    public void drive(double forward, double right, double turn, boolean slowMode) {

        forward = getProcessedInput(forward);
        right = getProcessedInput(right);
        turn = getProcessedInput(turn);

        double leftFrontPower = forward - right + turn;
        double leftBackPower = forward + right + turn;
        double rightFrontPower = forward - right - turn;
        double rightBackPower = forward + right - turn;
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

    //For Generated Auto Support:
    public void setTargetPosition(int[] encoders){
        moveEncoders(DRIVE_SPEED, encoders[0], encoders[1], encoders[2], encoders[3]);
    }
    public boolean isBusy(){
        return frontLeft.isBusy() || frontRight.isBusy() ||
                backLeft.isBusy() || backRight.isBusy();
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
