package model.map;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

public class MapMatrix {
    private final TileType[][] logicMatrix;
    private final int tileSize = 32;

    public MapMatrix(String imagePath, int rows, int cols) {
        logicMatrix = new TileType[rows][cols];
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        PixelReader reader = image.getPixelReader();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * tileSize;
                int y = row * tileSize;

                int r = (int)(reader.getColor(x, y).getRed() * 255);
                int g = (int)(reader.getColor(x, y).getGreen() * 255);
                int b = (int)(reader.getColor(x, y).getBlue() * 255);

                if (r == 0 && g == 0 && b == 0) {
                    logicMatrix[row][col] = TileType.ROAD;
                } else if (r == 138 && g == 136 && b == 138) {
                    logicMatrix[row][col] = TileType.SIDEWALK;
                } else if (r == 255 && g == 0 && b == 0) {
                    logicMatrix[row][col] = TileType.BUILDING;
                }else if (r == 255 && g == 255 && b == 255) {
                    logicMatrix[row][col] = TileType.CROSSWALK;
                } else {
                    logicMatrix[row][col] = TileType.OTHER;
                }
            }
        }
    }

    public boolean isWithinBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < logicMatrix.length && col < logicMatrix[0].length;
    }

    public boolean isWalkable(int row, int col) {
        TileType tile = logicMatrix[row][col];
        return tile == TileType.ROAD || tile == TileType.SIDEWALK;
    }

    public int getTileSize() {
        return tileSize;
    }

    public TileType getTileType(int row, int col) {
        if (isWithinBounds(row, col)) {
            return logicMatrix[row][col];
        }
        return TileType.OTHER;
    }
}
