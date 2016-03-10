package module;

public class InvalidMove extends Throwable{
	    private int r;
	    private int c;

	    public InvalidMove(int r, int c) {
	        this.r = r;
	        this.c = c;
	    }
	    
	    public String FormatError() {
	        return "invalid move on board at " + this.r + " and " + this.c;
	    }
}
