package org.firstinspires.ftc.teamcode.FreightFrenzyOpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FullBase;

@TeleOp(name = "Main TeleOp", group = "Linear Opmode")
public class MainTeleOp extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    FullBase Base ;


    boolean dpadUpHeld = false;
    boolean dpadDownHeld = false;
    boolean dpadRightHeld = false;
    boolean dpadLeftHeld  = false;
    boolean gamepad1XHeld = false;
    boolean gamepad1YHeld = false;

    boolean flickerPositon =true;
    boolean firstTime = true;
    boolean slowMode = false, isSlowMode = false;
    double initTime;
    int initPos;

    @Override
    public void runOpMode() {
        Base = new FullBase(telemetry,this, hardwareMap, true);
        telemetry.addData("Status", "Initialized");
        Base.getTelemetry().update();
        Base.init();
        Base.getTelemetry().addLine("Initialization Complete");
        Base.getTelemetry().update();
        waitForStart();
        while (opModeIsActive()){
            /*      drivetrain      */
            double forward = -gamepad1.left_stick_y;
            double right = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            if(gamepad1.x && !isSlowMode) {
                slowMode = !slowMode;
            }
            isSlowMode = gamepad1.x;

            Base.drivetrain.drive(forward, right, turn, slowMode);
            if(gamepad1.b) Base.drivetrain.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            /*      intake      */
            Base.sucker.moveArmInTeleop(gamepad1.right_bumper);

            Base.sucker.moveSuckerInTeleop(gamepad1.right_trigger);
            Base.getTelemetry().addData("Gamepad 1 Trigger: ",gamepad1.right_trigger);
            Base.getTelemetry().addData("Gamepad 2 Trigger: ",gamepad2.right_trigger);
            Base.getTelemetry().addData("Gamepad 2 A: ",gamepad1.a);
            Base.getTelemetry().addData("Gamepad 1 Left Trigger: ",gamepad1.left_trigger);
            Base.getTelemetry().addData("Gamepad 2 y: ",gamepad2.y);
            Base.sucker.moveSuckerInTeleop(gamepad1.right_trigger);
            if(gamepad1.right_trigger < .1)
                Base.sucker.moveSuckerInTeleop(-gamepad1.left_trigger);
            /*      outtake     */
           Base.outtakeBucket.changeTopInTeleOp(gamepad2.dpad_up, gamepad2.dpad_down);
           Base.outtakeBucket.dump(gamepad2.y);
           Base.getTelemetry().addData("Slider Position", Base.outtakeBucket.slideInTeleop(gamepad2.a));
           Base.getTelemetry().addData("Slider cURRENT pOSITION", Base.outtakeBucket.slider.getCurrentPosition());
           Base.getTelemetry().update();
//            Base.outtakeBucket.changeTopInTeleOp(gamepad2.dpad_up, gamepad2.dpad_down);
            /*   ducky spinner  */
            Base.duckeySpinner.spin(gamepad2.left_trigger, gamepad2.right_trigger);

        }
    }
}
