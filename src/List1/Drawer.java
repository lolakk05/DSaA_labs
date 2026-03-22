package List1;

public class Drawer {

	private static void drawLine(int n, char ch) {
		// TODO
        for(int i = 0; i < n; i++) {
            System.out.print(ch);
        }
	}


	public static void drawPyramid(int n) {
		// TODO
        if(n <= 0 || n >= 20) {
            System.out.println("ERROR");
            return;
        }
        for(int i = 0; i < n; i++) {
            drawLine(n-1-i, '.');
            drawLine(2*i+1, 'X');
            drawLine(n-1-i, '.');
            System.out.println();
        }
	}

	public static void drawChristmassTree(int n) {
		// TODO
        if(n <= 0) {
            System.out.println("ERROR");
            return;
        }
        for(int i = 0; i < n; i++) {
            for(int j = 0; j <= i; j++) {
                drawLine(n-1-j, '.');
                drawLine(j, 'X');
                if(i == 0) {
                    System.out.print("X");
                }
                else {
                    System.out.print("I");
                }
                drawLine(j, 'X');
                drawLine(n-1-j, '.');
                System.out.println();
            }
        }
        drawLine(n-1, '.');
        System.out.print("I");
        drawLine(n-1, '.');
        System.out.println();
	}
}
