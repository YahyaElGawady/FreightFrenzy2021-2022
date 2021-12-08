package org.firstinspires.ftc.teamcode.FreightFrenzyComponents;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.videoio.VideoCapture;

public class DuckDetector {
    VideoCapture cam;
    OpMode op;
    Mat img = new Mat();

    public static class Coord{
        public Point top_left, bottom_right;
        public Coord(Point tl, Point br){
            top_left = tl;
            bottom_right = br;
        }
    }
    //TODO: fill with accurate coordinates for possible duck positions
    public final static Coord LEFT_POS =
            new Coord(new Point(), new Point());
    public final static Coord MIDDLE_POS =
            new Coord(new Point(), new Point());
    public final static Coord RIGHT_POS =
            new Coord(new Point(), new Point());

//    public final static int ROWS = 0; // noTODO: fill with accurate number of rows
//    public final static int COLS = 0; // noTODO: fill with accurate number of columns
//    public final static int TYPE = 0; // noTODO: fill with type
    public final static RGB DUCKCOLOR = new RGB(0,0,0);

    public enum DuckLocation{ LEFT, MIDDLE, RIGHT}

    static public class RGB{
        public int red, blue, green;
        RGB(int red, int blue, int green){
            this.red = red;
            this.blue = blue;
            this.green = green;
        }

        public int getYellow(){
            return red + green - blue;
        }
    }
    DuckDetector(OpMode om){
        op = om;

        // Gets id. NB: id might not be accurate. Change if necessary
        int id = op.hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id",
                op.hardwareMap.appContext.getPackageName());
        cam = new VideoCapture(id);
    }
    public void takePicture(){
        cam.read(img);
    }
    public DuckLocation mostDuckyArea(){
        RGB leftAvg = averageColor(LEFT_POS),
                middleAvg = averageColor(MIDDLE_POS),
                rightAvg = averageColor(RIGHT_POS);
        int leftDuckyness  = Math.abs(DUCKCOLOR.getYellow() - leftAvg.getYellow());
        int midDuckyness   = Math.abs(DUCKCOLOR.getYellow() - middleAvg.getYellow());
        int rightDuckyness = Math.abs(DUCKCOLOR.getYellow() - rightAvg.getYellow());

        if(leftDuckyness < midDuckyness && leftDuckyness < rightDuckyness)
            return DuckLocation.LEFT;
        else if(rightDuckyness < midDuckyness && rightDuckyness < leftDuckyness)
            return DuckLocation.RIGHT;
        else
            return DuckLocation.MIDDLE;
    }
    public RGB averageColor(@NonNull Coord box){
        RGB ret = new RGB(0,0,0);
        int total = 0;

        for (int x = (int)box.top_left.x; x < box.bottom_right.x; ++x){
            for (int y = (int)box.top_left.y; y < box.bottom_right.y; ++y){
                ret.red += img.get(y,x)[0];
                ret.green += img.get(y,x)[1];
                ret.blue += img.get(y,x)[2];
                ++total;
            }
        }

        ret.red /= total;
        ret.green /= total;
        ret.blue /= total;

        return ret;
    }
}
