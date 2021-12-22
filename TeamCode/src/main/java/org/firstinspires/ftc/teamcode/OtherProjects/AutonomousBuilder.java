package org.firstinspires.ftc.teamcode.OtherProjects;

import android.annotation.SuppressLint;

import org.firstinspires.ftc.teamcode.FullBase;

import java.io.DataOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.function.Consumer;

public class AutonomousBuilder{
    String name;
    Path path_to_auto;
    DataOutputStream out;

    static final String PATH_TO_AUTOS = "/../FreightFrenzyOpMode/Autonomous/";

    public interface Task{
        public void execute(FullBase base, String[] components);
    }
    public class TimerTask implements Task{
        Consumer<FullBase> function;
        public TimerTask(Consumer<FullBase> consumer){
            function = consumer;
        }
        @SuppressLint("NewApi")
        @Override
        public void execute(FullBase base, String[] components){
            long begin = System.nanoTime();
            function.accept(base);
            long end = System.nanoTime();
            try {
                for (String s : components) {
                    out.writeChars(String.format("%s.setPowerInAuto(true)\n", s));
                }
                out.writeChars(String.format("try{Thread.sleep(%d);}catch(Exception e){}\n",
                        end - begin));
            } catch(Exception e){}
        }
    }
    public class EncoderTask implements Task{

        @Override
        public void execute(FullBase base, String[] components){

        }
    }

    @SuppressLint("NewApi")
    public AutonomousBuilder(String name){
        this.name = name;
        this.path_to_auto = Paths.get(PATH_TO_AUTOS + name + ".java");
        try {
            this.out = new DataOutputStream(
                    Files.newOutputStream(path_to_auto,
                            StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING));
        } catch (Exception e){}
    }
    public void createAuto(){

    }
}
