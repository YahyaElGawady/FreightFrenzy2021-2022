package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

public class DuckDetector {
//    VideoCapture cam;
//    OpMode op;
//    Mat img = new Mat();
//
//    public static class Coord{
//        public Point top_left, bottom_right;
//        public Coord(Point tl, Point br){
//            top_left = tl;
//            bottom_right = br;
//        }
//    }
//    //TODO: fill with accurate coordinates for possible duck positions
//    public final static Coord LEFT_POS =
//            new Coord(new Point(10,10), new Point(30,30));
//    public final static Coord MIDDLE_POS =
//            new Coord(new Point(50,10), new Point(70,30));
//    public final static Coord RIGHT_POS =
//            new Coord(new Point(90,10), new Point(110,30));
//
////    public final static int ROWS = 0; // noTODO: fill with accurate number of rows
////    public final static int COLS = 0; // noTODO: fill with accurate number of columns
////    public final static int TYPE = 0; // noTODO: fill with type
//    public final static RGB DUCKCOLOR = new RGB(255,255,0);
//
//    public enum DuckLocation{ LEFT, MIDDLE, RIGHT}
//
//    static public class RGB{
//        public int red, blue, green;
//        RGB(int red, int green, int blue){
//            this.red = red;
//            this.blue = blue;
//            this.green = green;
//        }
//
//        public int getYellow(){
//            return red + green  - blue;
//        }
//    }
//    public DuckDetector(OpMode om){
//        op = om;
//
//        // Gets id. NB: id might not be accurate. Change if necessary
//        int id = op.hardwareMap.appContext.getResources().getIdentifier(
//                "cameraMonitorViewId", "id",
//                op.hardwareMap.appContext.getPackageName());
//        cam = new VideoCapture(id);
//
//    }
//    public void takePicture(){
//        cam.read(img);
//    }
//    public DuckLocation mostDuckyArea(){
//        RGB leftAvg = averageColor(LEFT_POS),
//                middleAvg = averageColor(MIDDLE_POS),
//                rightAvg = averageColor(RIGHT_POS);
//        int leftDuckyness  = Math.abs(DUCKCOLOR.getYellow() - leftAvg.getYellow());
//        int midDuckyness   = Math.abs(DUCKCOLOR.getYellow() - middleAvg.getYellow());
//        int rightDuckyness = Math.abs(DUCKCOLOR.getYellow() - rightAvg.getYellow());
//
//        if(leftDuckyness < midDuckyness && leftDuckyness < rightDuckyness)
//            return DuckLocation.LEFT;
//        else if(rightDuckyness < midDuckyness && rightDuckyness < leftDuckyness)
//            return DuckLocation.RIGHT;
//        else
//            return DuckLocation.MIDDLE;
//    }
//    public RGB averageColor(@NonNull Coord box){
//        RGB ret = new RGB(0,0,0);
//        int total = 0;
//
//        for (int x = (int)box.top_left.x; x < box.bottom_right.x; ++x){
//            for (int y = (int)box.top_left.y; y < box.bottom_right.y; ++y){
//                ret.red += img.get(y,x)[0];
//                ret.green += img.get(y,x)[1];
//                ret.blue += img.get(y,x)[2];
//                ++total;
//            }
//        }
//
//        ret.red /= total;
//        ret.green /= total;
//        ret.blue /= total;
//
//        return ret;
//    }
OpMode opMode;
    OpenCvCamera camera;

    CustomPipeline pipeline;

    private final Point BLUE_LEFT_LEFT_TL    = new Point(0,130);
    private final Point BLUE_LEFT_LEFT_BR    = new Point(55, 170);
    private final Point BLUE_LEFT_MIDDLE_TL    = new Point(130,130);
    private final Point BLUE_LEFT_MIDDLE_BR    = new Point(185, 170);

    private final Point BLUE_RIGHT_LEFT_TL    = new Point(0,130);
    private final Point BLUE_RIGHT_LEFT_BR   = new Point(55, 170);
    private final Point BLUE_RIGHT_MIDDLE_TL    = new Point(130,130);
    private final Point BLUE_RIGHT_MIDDLE_BR  = new Point(185, 170);




    private final Point RED_RIGHT_MIDDLE_TL    = new Point(80, 130);
    private final Point RED_RIGHT_MIDDLE_BR    = new Point(120,170);
    private final Point RED_RIGHT_RIGHT_TL    = new Point(210,130);
    private final Point RED_RIGHT_RIGHT_BR    = new Point(250, 170);

    private final Point RED_LEFT_MIDDLE_TL    = new Point(50, 140);
    private final Point RED_LEFT_MIDDLE_BR    = new Point(105,180);
    private final Point RED_LEFT_RIGHT_TL    = new Point(200,140);
    private final Point RED_LEFT_RIGHT_BR    = new Point(245, 180);

    private Point middleTL;
    private Point middleBR;
    private Point rightTL;
    private Point rightBR;

    private RGB middleBox;
    private RGB rightBox;
    private boolean show_value = true;
    boolean isRed, isLeft;
    public enum DuckLocation{
        LEFT, RIGHT, MIDDLE
    }
    public DuckDetector(OpMode op, boolean isRed, boolean isLeft){

        opMode = op;
        this.isLeft = isLeft;
        this.isRed = isRed;
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(opMode.hardwareMap.get(WebcamName.class, "camera"), cameraMonitorViewId);

        pipeline = new CustomPipeline();
        camera.openCameraDevice();
        camera.setPipeline(pipeline);
        camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);

        if(isRed) {
            middleTL = (isLeft) ? RED_LEFT_MIDDLE_TL : RED_RIGHT_MIDDLE_TL;
            middleBR = (isLeft) ? RED_LEFT_MIDDLE_BR : RED_RIGHT_MIDDLE_BR;
            rightTL = (isLeft) ? RED_LEFT_RIGHT_TL : RED_RIGHT_RIGHT_TL;
            rightBR = (isLeft) ? RED_LEFT_RIGHT_BR : RED_RIGHT_RIGHT_BR;
        } else{
            middleTL = (isLeft) ? BLUE_LEFT_MIDDLE_TL : BLUE_RIGHT_MIDDLE_TL;
            middleBR = (isLeft) ? BLUE_LEFT_MIDDLE_BR : BLUE_RIGHT_MIDDLE_BR;
            rightTL = (isLeft) ? BLUE_LEFT_LEFT_TL : BLUE_RIGHT_LEFT_TL;
            rightBR = (isLeft) ? BLUE_LEFT_LEFT_BR : BLUE_RIGHT_LEFT_BR;
        }
    }

    public void stopStreaming(){
        camera.stopStreaming();
    }

    public DuckLocation mostDuckyArea(){

        int middleBoxValue = middleBox.getBlack();
        int rightBoxValue = rightBox.getBlack();

        if (show_value){
            opMode.telemetry.addData("Middle Box Value: ", middleBoxValue);
            opMode.telemetry.addData("Right Box Value: ", rightBoxValue);
        }
        int dif = middleBoxValue - rightBoxValue;
        if(isRed && !isLeft) {
            if (dif <= 0) {
                opMode.telemetry.addLine("MIDDLE");
                return DuckLocation.MIDDLE;
            } else if (Math.abs(dif) < 240) {
                opMode.telemetry.addLine("LEFT");
                return DuckLocation.LEFT;
            } else{
                opMode.telemetry.addLine("RIGHT");
                return DuckLocation.RIGHT;
            }
        } else if(isRed && isLeft) {
            if (dif < -50) {
                opMode.telemetry.addLine("MIDDLE");
                return DuckLocation.MIDDLE;
            } else if (dif < 50) {
                opMode.telemetry.addLine("LEFT");
                return DuckLocation.LEFT;
            } else
            {
                opMode.telemetry.addLine("RIGHT");
                return DuckLocation.RIGHT;
            }
        } else if(!isRed && !isLeft) {
            if (dif > 50) {
                opMode.telemetry.addLine("MIDDLE");
                return DuckLocation.MIDDLE;
            } else if (dif < -50) {
                opMode.telemetry.addLine("RIGHT");
                return DuckLocation.RIGHT;
            } else{
                opMode.telemetry.addLine("LEFT");
                return DuckLocation.LEFT;
            }
        } else {
            if (dif > 50) {
                opMode.telemetry.addLine("MIDDLE");
                return DuckLocation.MIDDLE;
            } else if (dif < -50) {
                opMode.telemetry.addLine("RIGHT");
                return DuckLocation.RIGHT;
            } else{
                opMode.telemetry.addLine("LEFT");
                return DuckLocation.LEFT;
            }
        }
    }

    class CustomPipeline extends OpenCvPipeline {

        @Override
        public Mat processFrame(Mat input){

            middleBox = getAverageColor(input, middleTL, middleBR);
            rightBox = getAverageColor(input, rightTL, rightBR);

            int thickness = 3;

            Scalar leftColor = new Scalar(255,0,0);
            Scalar middleColor = new Scalar(255,0,0);
            Scalar rightColor = new Scalar(255,0,0);
            DuckLocation position = mostDuckyArea();
            if (position == DuckLocation.LEFT){
                leftColor = new Scalar(0,255,0);
            }
            else if (position == DuckLocation.RIGHT){
                rightColor = new Scalar(0,255,0);
            } else{
                middleColor = new Scalar(0,255,0);
            }

            Imgproc.rectangle(input, middleTL, middleBR, middleColor, thickness);
            Imgproc.rectangle(input, rightTL, rightBR, rightColor, thickness);

            sendTelemetry();

            return input;
        }

        private RGB getAverageColor(Mat mat, Point topLeft, Point bottomRight){
            int red = 0;
            int green = 0;
            int blue = 0;
            int total = 0;

            for (int x = (int)topLeft.x; x < bottomRight.x; x++){
                for (int y = (int)topLeft.y; y < bottomRight.y; y++){
                    red += mat.get(y,x)[0];
                    green += mat.get(y,x)[1];
                    blue += mat.get(y,x)[2];
                    total++;
                }
            }

            red /= total;
            green /= total;
            blue /= total;

            return new RGB(red, green, blue);
        }

        private void sendTelemetry(){
            opMode.telemetry.addLine("MIDDLE :" + " R " + middleBox.red + " G " + middleBox.green+ " B " + middleBox.blue);
            opMode.telemetry.addLine("RIGHT :" + " R " + rightBox.red + " G " + rightBox.green+ " B " + rightBox.blue);
            opMode.telemetry.update();
        }

    }

    public void setTelemShow(boolean show){
        this.show_value = show;
    }

}
class RGB {
    public int red = 0;
    public int green = 0;
    public int blue = 0;

    public RGB(){

    }
    public RGB(int r, int g, int b){
        red = r;
        green = g;
        blue=  b;
    }

    public void setRed(int r)
    {
        red = r;
    }
    public void setGreen(int g) {
        green = g;
    }
    public void setBlue(int b)
    {
        blue = b;
    }
    public int getBlack(){
        return green + blue + red;
    }
}
