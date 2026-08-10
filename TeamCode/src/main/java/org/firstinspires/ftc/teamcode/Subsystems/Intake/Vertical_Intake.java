package org.firstinspires.ftc.teamcode.Subsystems.Intake;
import static com.pedropathing.ivy.commands.Commands.instant;
import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Vertical_Intake {
    public enum State {VGRAB, VDEPOSIT};
    private final Servo VerticalClaw;

    State state = State.VDEPOSIT;
    public Vertical_Intake(HardwareMap hardwareMap){
        VerticalClaw = hardwareMap.get(Servo.class, "VerticalClaw");
    }

    public void setState (State newState){
        state = newState;
        switch (newState){
            case VGRAB:
                VerticalClaw.setPosition(0.7); // Assumed grip position for claw servo
                break;
            case VDEPOSIT:
                VerticalClaw.setPosition(0.2); // Assumed deposit position for claw servo

        }
    }

    public Command vgrab(){
        return instant(() -> setState(State.VGRAB)).requiring(this);
    }
    public Command vdeposit(){
        return instant(() -> setState(State.VDEPOSIT)).requiring(this);
    }
}
