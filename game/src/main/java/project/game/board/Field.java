package project.game.board;


    import javafx.scene.paint.Paint;
    import javafx.scene.shape.Circle;

    public class Field extends Circle {
        private FieldsColor fieldColor;
        private int yCord, xCord;
        public Field(int i, int y, int x) {
        	this.yCord = y;
            this.xCord = x;
            this.fieldColor = FieldsColor.values()[i];
            this.setStroke(Paint.valueOf("BLACK"));
        }

        public Field(FieldsColor color, int y, int x) {
            this.fieldColor = color;
            this.yCord = y;
            this.xCord = x;
            this.setStroke(Paint.valueOf("BLACK"));
        }

        static Field getNullField() {
            Field nullField = new Field(0, -1, -1);
            nullField.setStroke(Paint.valueOf("TRANSPARENT"));
            return nullField;
        }

        public void setColor(FieldsColor color) {
            this.fieldColor = color;
        }

        public String getColor() throws NullPointerException {
            return this.fieldColor.getColor();
        }

        public FieldsColor getFieldColor() {
            return this.fieldColor;
        }

        public int getYCord() {
            return yCord;
        }

        public int getXCord() {
            return xCord;
        }
    }