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
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class AutonomousBuilder{
    String name;
    Path path_to_auto;
    DataOutputStream out;

    static final String PATH_TO_AUTOS = "/../FreightFrenzyOpMode/Autonomous/";

    public interface Task{}
    public class TimerTask implements Task{
        Consumer<FullBase> function;
        public TimerTask(Consumer<FullBase> consumer){
            function = consumer;
        }
        @SuppressLint("NewApi")
        public void execute(FullBase base, String[] components){
            long begin = System.nanoTime();
            function.accept(base);
            long end = System.nanoTime();
            try {
                for (String s : components) {
                    out.writeChars(String.format("%s.setPowerInAuto(1);\n", s));
                }
                out.writeChars(String.format("try{Thread.sleep(%d);}catch(Exception e){}\n",
                        end - begin));
                for(String s : components){
                    out.writeChars(String.format("%s.setPowerInAuto(0);\n", s));
                }
            } catch(Exception e){}
        }
    }


    public class EncoderTask implements Task{
        public class RetType{
            ChildComponent[] childComponents;
            MainComponent[]  mainComponents;
        }
        public class ChildComponent {
            String component;
        }
        public class MainComponent  {
            int[] encoders;
            String component;
        }
        Function<FullBase, RetType> function;

        public EncoderTask(Function<FullBase, RetType> function){
            this.function = function;
        }

        @SuppressLint("NewApi")
        public void execute(FullBase base){
            RetType retType = function.apply(base);
            try{
                for(ChildComponent info: retType.childComponents){
                    out.writeChars(String.format("%s.setPowerInAuto(true);\n", info.component));
                }
                for(MainComponent info: retType.mainComponents){
                    out.writeChars(
                            String.format("%s.setTargetPosition(new int[]{", info.component));
                    out.writeInt(info.encoders[0]);
                    for(int i = 1; i < info.encoders.length; ++i){
                        out.writeChars(String.format(",%d", info.encoders[i]));
                    }
                    out.writeChars("});\n");
                }

                out.writeChars(
                        String.format("while(%s.isBusy()",
                                retType.mainComponents[0].component));
                for(int i = 1; i < retType.mainComponents.length; ++i){
                    out.writeChars(
                            String.format(" || %s.isBusy()",
                                    retType.mainComponents[i].component));
                }
                out.writeChars("){}\n");
                for(ChildComponent info: retType.childComponents){
                    out.writeChars(
                            String.format("%s.setPowerInAuto(false);\n", info.component));
                }
            } catch(Exception e){}
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
