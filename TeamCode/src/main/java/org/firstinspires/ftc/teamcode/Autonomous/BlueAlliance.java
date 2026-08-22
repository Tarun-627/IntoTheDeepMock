package org.firstinspires.ftc.teamcode.Autonomous;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.paths.PathChain;
import static com.pedropathing.ivy.pedro.PedroCommands.*;
import static org.firstinspires.ftc.teamcode.PedroPathing.Tuning.follower;

import static com.pedropathing.ivy.groups.Groups.sequential;


public class BlueAlliance {

    private final Pose startPos = new Pose(103.32441, 33.3974358, Math.toRadians(90)); // random positions based upon pedro pathing visualizer for decode
    private final Pose grabPos = new Pose(70.35562, 49.848383, Math.toRadians(90)); // random positions based upon pedro pathing visualizer for decode
    private final Pose scorePos = new Pose(60, 84, Math.toRadians(135)); // random positions based upon pedro pathing visualizer for decode

    //defining our PathChains
    private PathChain grabArtifact, scoreArtifact;

    public void buildPaths() {
        grabArtifact = follower.pathBuilder()
                .addPath(new BezierLine(startPos, grabPos))
                .setLinearHeadingInterpolation(startPos.getHeading(), grabPos.getHeading())
                .build();

        scoreArtifact = follower.pathBuilder()
                .addPath(new BezierLine(grabPos, scorePos))
                .setLinearHeadingInterpolation(grabPos.getHeading(), scorePos.getHeading())
                .build();

    }

}
