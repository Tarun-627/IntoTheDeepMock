package org.firstinspires.ftc.teamcode.Subsystems.Intake;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakePivot {
    public enum State {NORMAL, PIVOT};
    private final Servo intakepivot;

    State state = State.NORMAL;
    public IntakePivot(HardwareMap hardwareMap){
        intakepivot = hardwareMap.get(Servo.class, "Intake Pivot");
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case NORMAL:
                intakepivot.setPosition(-1.0); // Assumed for position for claw servo rotator
                break;
            case PIVOT:
                intakepivot.setPosition(1.0); // Assumed for position for claw servo rotator
                break;

        }
    }

    public Command intakeinitial(){
        return instant(() -> setState(State.NORMAL)).requiring(this);
    }
    public Command intakepivoted(){
        return instant(() -> setState(State.PIVOT)).requiring(this);
    }
}
