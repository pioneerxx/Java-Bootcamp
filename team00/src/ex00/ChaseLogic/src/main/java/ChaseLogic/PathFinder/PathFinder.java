package ChaseLogic.PathFinder;

import java.util.Vector;
import ChaseLogic.CoordinatesPair.CoordinatesPair;

public class PathFinder {
    private int[][] map;
    private Vector<Vector<Integer>> verticalMatrixVector;
    private Vector<Vector<Integer>> horizontalMatrixVector;
    private int[][] verticalMatrixInt;
    private int[][] horizontalMatrixInt;
    private CoordinatesPair start;
    private CoordinatesPair end;
    private int iter;
    private Vector<Vector<Integer>>  matrixObserver;
    private Vector<CoordinatesPair> solution;
    private CoordinatesPair step;

    public Vector<CoordinatesPair> getSolution() {
//            for (CoordinatesPair pair : solution) {
//                System.out.println(pair.getX() + " " + pair.getY());
//            }
        return solution;
    }

    public CoordinatesPair getStep() {
        return step;
    }

    public PathFinder(int[][] map) {
        this.map = map;
    }

    public void setCoordinates(int startX, int startY, int endX, int endY, boolean isEnemy) {
        this.start = new CoordinatesPair(startX, startY);
        this.end = new CoordinatesPair(endX, endY);
        horizontalMatrixInt = new int[map.length][map.length];
        verticalMatrixInt = new int[map.length][map.length];
        convertMap(isEnemy);
        horizontalMatrixVector = convertIntToVector(horizontalMatrixInt);
        verticalMatrixVector = convertIntToVector(verticalMatrixInt);
        matrixObserver = new Vector<> ();
        solution = new Vector<>();
        for (int i = 0; i < horizontalMatrixVector.get(0).size(); i++) {
            Vector<Integer> row = new Vector<>();
            for (int j = 0; j <  verticalMatrixVector.size(); j++) {
                row.add(0);
            }
            matrixObserver.add(row);
        }
        try {
            matrixObserver.get(startX).set(startY, -1);
            matrixObserver.get(endX).set(endY, -2);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

    public Vector<Vector<Integer>> getHorizontalMatrixVector() {
        return horizontalMatrixVector;
    }

    public Vector<Vector<Integer>> getVerticalMatrixVector() {
        return verticalMatrixVector;
    }

    private void convertMap(boolean isEnemy) {
        int[][] updatedMap;
        if (isEnemy) {
            updatedMap = updatedMapForEnemy();
        } else {
            updatedMap = updatedMapForPlayer();
        }
        for (int i = 0; i < updatedMap.length; i++) {
            for (int j = 0; j < updatedMap.length; j++) {
                if (updatedMap[i][j] == 1 || j + 1 == updatedMap.length) {
                    horizontalMatrixInt[i][j] = 1;
                } else if (j + 1 < updatedMap.length) {
                    if (updatedMap[i][j + 1] == 1) {
                        horizontalMatrixInt[i][j] = 1;
                    }
                } else {
                    horizontalMatrixInt[i][j] = 0;
                }
            }
        }
        for (int i = 0; i < updatedMap.length; i++) {
            for (int j = 0; j < updatedMap.length; j++) {
                if (updatedMap[i][j] == 1 || i + 1 == updatedMap.length) {
                    verticalMatrixInt[i][j] = 1;
                } else if (i + 1 < updatedMap.length) {
                    if (updatedMap[i + 1][j] == 1) {
                        verticalMatrixInt[i][j] = 1;
                    }
                } else {
                    verticalMatrixInt[i][j] = 0;
                }
            }
        }
    }

    public boolean isPassable() {
        boolean result = false;
        Vector<CoordinatesPair> avable = new Vector<CoordinatesPair>();
        Vector<CoordinatesPair> tempAvable = new Vector<CoordinatesPair>();
        tempAvable.add(start);
        int size;
        iter = 1;
        try {
            size = matrixObserver.get(0).size() * matrixObserver.size();
            while (true) {
                avable = new Vector<>(tempAvable);
                for (CoordinatesPair current : avable) {
                    if (rightObserve(current, tempAvable)) {
                        result = true;
                        break;
                    } else if (downObserve(current, tempAvable)) {
                        result = true;
                        break;
                    } else if (leftObserve(current, tempAvable)) {
                        result = true;
                        break;
                    } else if (upObserve(current, tempAvable)) {
                        result = true;
                        break;
                    }
                }
                if (result) break;
                iter++;
                if (iter == size + 1) break;
            }
            if (result == true) backtrack();
            step = solution.elementAt((solution.size() - 2));

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {}
        return result;
    }

    private void backtrack() {
        solution.add(end);
        while (iter != 1) {
            iter--;
            if (upBacktrack()) continue;
            if (leftBacktrack()) continue;
            if (downBacktrack()) continue;
            if (rightBacktrack()) continue;
        }
        solution.add(start);
    }

    private boolean upBacktrack() {
        boolean result = false;
        if (solution.lastElement().getX() != 0) {
            if (matrixObserver.get(solution.lastElement().getX() - 1).get(solution.lastElement().getY()) == iter &&
                    verticalMatrixVector.get(solution.lastElement().getX() - 1).get(solution.lastElement().getY()) == 0) {
                solution.add(new CoordinatesPair(solution.lastElement().getX() - 1, solution.lastElement().getY()));
                result = true;
            }
        }
        return result;
    }

    private boolean leftBacktrack() {
        boolean result = false;
        if (solution.lastElement().getY() != 0) {
            if (matrixObserver.get(solution.lastElement().getX()).get(solution.lastElement().getY() - 1) == iter &&
                    horizontalMatrixVector.get(solution.lastElement().getX()).get(solution.lastElement().getY() - 1) == 0) {
                solution.add(new CoordinatesPair(solution.lastElement().getX(), solution.lastElement().getY() - 1));
                result = true;
            }
        }
        return result;
    }

    private boolean downBacktrack() {
        boolean result = false;
        if (solution.lastElement().getX() != (int)(verticalMatrixVector.size() - 1)) {
            if (matrixObserver.get(solution.lastElement().getX() + 1).get(solution.lastElement().getY()) == iter &&
                    verticalMatrixVector.get(solution.lastElement().getX()).get(solution.lastElement().getY()) == 0) {
                solution.add(new CoordinatesPair(solution.lastElement().getX() + 1, solution.lastElement().getY()));
                result = true;
            }
        }
        return result;
    }

    private boolean rightBacktrack() {
        boolean result = false;
        if (solution.lastElement().getY() != (int)(horizontalMatrixVector.get(0).size() - 1)) {
            if (matrixObserver.get(solution.lastElement().getX()).get(solution.lastElement().getY() + 1) == iter &&
                    horizontalMatrixVector.get(solution.lastElement().getX()).get(solution.lastElement().getY()) == 0) {
                solution.add(new CoordinatesPair(solution.lastElement().getX(), solution.lastElement().getY() + 1));
                result = true;
            }
        }
        return result;
    }



    private Vector<Vector<Integer>> convertIntToVector(int[][] array) {
        Vector<Vector<Integer>> vectorVector = new Vector<Vector<Integer>>();
        for (int[] row : array) {
            Vector<Integer> vector = new Vector<>();
            for (int value : row) {
                vector.add(value);
            }
            vectorVector.add(vector);
        }
        return vectorVector;
    }

    private boolean rightObserve(CoordinatesPair current, Vector<CoordinatesPair> tempAvable) throws ArrayIndexOutOfBoundsException {
        boolean result = false;
        int second = current.getY();
        int matrixHorizontalSize = horizontalMatrixVector.get(0).size();
        if (second != matrixHorizontalSize - 1) {
            int first = current.getX();
            if (horizontalMatrixVector.get(first).get(second) == 0 && matrixObserver.get(first).get(second + 1) == 0) {
                matrixObserver.get(first).set(second + 1, iter);
                tempAvable.add(new CoordinatesPair(first, second + 1));
            } else if (horizontalMatrixVector.get(first).get(second) == 0 && matrixObserver.get(first).get(second + 1) == -2) {
                result = true;
            }
        }
        return result;
    }

    private boolean downObserve(CoordinatesPair current, Vector<CoordinatesPair> tempAvable) throws ArrayIndexOutOfBoundsException {
        boolean result = false;
        if (current.getX() != verticalMatrixVector.size() - 1) {
            if (verticalMatrixVector.get(current.getX()).get(current.getY()) == 0 &&
                    matrixObserver.get(current.getX() + 1).get(current.getY()) == 0) {
                matrixObserver.get(current.getX() + 1).set(current.getY(), iter);
                tempAvable.add(new CoordinatesPair(current.getX() + 1, current.getY()));
            } else if (verticalMatrixVector.get(current.getX()).get(current.getY()) == 0 &&
                    matrixObserver.get(current.getX() + 1).get(current.getY()) == -2) {
                result = true;
            }
        }
        return result;
    }


    private boolean leftObserve(CoordinatesPair current, Vector<CoordinatesPair> tempAvable) throws ArrayIndexOutOfBoundsException {
        boolean result = false;
        if (current.getY() != 0) {
            if (horizontalMatrixVector.get(current.getX()).get(current.getY() - 1) == 0 &&
                    matrixObserver.get(current.getX()).get(current.getY() - 1) == 0) {
                matrixObserver.get(current.getX()).set(current.getY() - 1, iter);
                tempAvable.add(new CoordinatesPair(current.getX(), current.getY() - 1));
            } else if (horizontalMatrixVector.get(current.getX()).get(current.getY() - 1) == 0 &&
                    matrixObserver.get(current.getX()).get(current.getY() - 1) == -2) {
                result = true;
            }
        }
        return result;
    }

    private boolean upObserve(CoordinatesPair current, Vector<CoordinatesPair> tempAvable) throws ArrayIndexOutOfBoundsException {
        boolean result = false;
        if (current.getX() != 0) {
            if (verticalMatrixVector.get(current.getX() - 1).get(current.getY()) == 0 &&
                    matrixObserver.get(current.getX() - 1).get(current.getY()) == 0) {
                matrixObserver.get(current.getX() - 1).set(current.getY(), iter);
                tempAvable.add(new CoordinatesPair(current.getX() - 1, current.getY()));
            } else if (verticalMatrixVector.get(current.getX() - 1).get(current.getY()) == 0 &&
                    matrixObserver.get(current.getX() - 1).get(current.getY()) == -2) {
                result = true;
            }
        }
        return result;
    }

    private int[][] updatedMapForPlayer() {
        int[][] updatedMap = new int[map.length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 1 || map[i][j] == 2) {
                    updatedMap[i][j] = 0;
                } else if (map[i][j] == 3) {
                    updatedMap[i][j] = 1;
                } else if (map[i][j] == 4) {
                    updatedMap[i][j] = 1;
                } else if (j > 1) {
                    updatedMap[i][j] = map[i][j - 1] == 3 ? 1 : 0;
                } else if (j < map.length - 1) {
                    updatedMap[i][j] = map[i][j + 1] == 3 ? 1 : 0;
                } else if (i < map.length - 1) {
                    updatedMap[i][j] = map[i + 1][j] == 3 ? 1 : 0;
                } else if (i  > 1) {
                    updatedMap[i][j] = map[i - 1][j] == 3 ? 1 : 0;
                } else {
                    updatedMap[i][j] = 0;
                }
            }
        }
        return updatedMap;
    }

    private int[][] updatedMapForEnemy() {
        int[][] updatedMap = new int[map.length][map.length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if ((i == start.getX() && j == start.getY()) || map[i][j] == 2) {
                    updatedMap[i][j] = 0;
                } else if (map[i][j] == 3 || map[i][j] == 4 || map[i][j] == 1) {
                    updatedMap[i][j] = 1;
                } else {
                    updatedMap[i][j] = 0;
                }
            }
        }
        return updatedMap;
    }
}
