package project.game.board;


    import javafx.scene.paint.Paint;
    import javafx.scene.shape.Circle;

    /**
     * @author danieldrapala
     *
     */
    public class Field extends Circle {
        private FieldsColor fieldColor;
        private int yCord, xCord;
        
        /**
         * @param i
         * @param y
         * @param x
         */
        public Field(int i, int y, int x) {
        	this.yCord = y;
            this.xCord = x;
            this.fieldColor = FieldsColor.values()[i];
            this.setStroke(Paint.valueOf("BLACK"));
            this.setStrokeWidth(2);
        }

        /**
         * @param color
         * @param y
         * @param x
         */
        public Field(FieldsColor color, int y, int x) {
            this.fieldColor = color;
            this.yCord = y;
            this.xCord = x;
            this.setStroke(Paint.valueOf("BLACK"));
        }

        /**
         * @return Field of Null place
         */
        public static Field getNullField() {
            Field nullField = new Field(8, -1, -1);
            nullField.setStroke(Paint.valueOf("TRANSPARENT"));
            nullField.setRadius(1);
            return nullField;
        }

        /**
         * @param color
         */
        public void setColor(FieldsColor color) {
            this.fieldColor = color;
        }

        /**
         * @return String of this Field Color 
         * @throws NullPointerException
         */
        public String getColor() throws NullPointerException {
            return this.fieldColor.getColor();
        }

        /**
         * @return return FieldsColor
         */
        public FieldsColor getFieldColor() {
            return this.fieldColor;
        }

        /**
         * @return y coordinate
         */
        public int getYCord() {
            return yCord;
        }

        /**
         * @return x coordinate
         */
        public int getXCord() {
            return xCord;
        }
    }