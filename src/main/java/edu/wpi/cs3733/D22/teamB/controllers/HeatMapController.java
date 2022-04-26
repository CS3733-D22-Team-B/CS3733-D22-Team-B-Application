package edu.wpi.cs3733.D22.teamB.controllers;

import eu.hansolo.fx.charts.heatmap.*;
import eu.hansolo.fx.charts.tools.ColorMapping;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class HeatMapController {
  @FXML private StackPane pane;
  private HeatMap heatMap;

  public void initialize() {
    heatMap =
        HeatMapBuilder.create()
            .prefSize(400, 400)
            .colorMapping(ColorMapping.LIME_YELLOW_RED)
            .spotRadius(20)
            .opacityDistribution(OpacityDistribution.CUSTOM)
            .fadeColors(true)
            .build();

    heatMap.setOnMouseMoved(e -> heatMap.addSpot(e.getX(), e.getY()));

    pane.getChildren().add(heatMap);

    pane.addEventFilter(
        MouseEvent.MOUSE_MOVED,
        event -> {
          double x = event.getX();
          double y = event.getY();
          if (x < heatMap.getSpotRadius()) x = heatMap.getSpotRadius();
          if (x > pane.getWidth() - heatMap.getSpotRadius())
            x = pane.getWidth() - heatMap.getSpotRadius();
          if (y < heatMap.getSpotRadius()) y = heatMap.getSpotRadius();
          if (y > pane.getHeight() - heatMap.getSpotRadius())
            y = pane.getHeight() - heatMap.getSpotRadius();

          heatMap.addSpot(x, y);
        });
    pane.widthProperty()
        .addListener(
            (ov, oldWidth, newWidth) -> heatMap.setSize(newWidth.doubleValue(), pane.getHeight()));
    pane.heightProperty()
        .addListener(
            (ov, oldHeight, newHeight) ->
                heatMap.setSize(pane.getWidth(), newHeight.doubleValue()));
  }
}
