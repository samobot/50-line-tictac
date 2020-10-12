import java.awt.Rectangle
import java.awt.event.ActionEvent

import javax.swing._

object TicTac {

    var positions: Array[Rectangle] = Array(new Rectangle(50, 50, 50, 50), new Rectangle(150, 50, 50, 50), new Rectangle(250, 50, 50, 50),
        new Rectangle(50, 150, 50, 50), new Rectangle(150, 150, 50, 50), new Rectangle(250, 150, 50, 50),
        new Rectangle(50, 250, 50, 50), new Rectangle(150, 250, 50, 50), new Rectangle(250, 250, 50, 50))

    def main(args: Array[String]): Unit = {
        try UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel") catch {case e: Exception => e.printStackTrace()}
        var isXTurn = true
        val board = Array(Array(" ", " ", " "), Array(" ", " ", " "), Array(" ", " ", " "))
        val frame = new JFrame("TicTac")
        frame.setResizable(false)
        val panel = new JPanel()
        panel.setLayout(null)
        val gameLabel = new JLabel("New game of TicTac: X Turn")
        gameLabel.setBounds(10, 10, 200, 20)
        val buttons = new Array[JButton](9)
        for(x <- 0 to 8) {
            buttons(x) = new JButton(" "); buttons(x).setBounds(positions(x))
            buttons(x).addActionListener((_: ActionEvent) => {
                val turn = if(isXTurn) "X" else "O"
                board(x/3)(x % 3) = turn; buttons(x).setText(turn)
                isXTurn = !isXTurn
                gameLabel.setText(if(isXTurn) "X Turn" else "O Turn")
                buttons(x).setEnabled(false)
                val result = check(board)
                if(result == "X" || result == "O") {gameLabel.setText(result + " Wins!"); buttons.foreach(U => U.setEnabled(false))}
                else if(buttons.forall(U => !U.isEnabled)) gameLabel.setText("Tie! Well it is TicTac after all.")
            })
        }

        panel.add(gameLabel)
        buttons.foreach(U => panel.add(U))
        frame.setContentPane(panel)
        frame.setSize(350, 380)
        frame.setVisible(true)
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE)
    }
    def check(board: Array[Array[String]]): String = {
        board.foreach(U => if(U(0) == U(1) && U(1) == U(2) && U(0) != " ") return U(0))
        for(x <- 0 to 2 if board(0)(x) == board(1)(x) && board(1)(x) == board(2)(x) && board(0)(x) != " ") return board(0)(x)
        if(board(0)(0) == board(1)(1) && board(1)(1) == board(2)(2) && board(0)(0) != " ") return board(0)(0)
        if(board(0)(2) == board(1)(1) && board(1)(1) == board(2)(0) && board(0)(2) != " ") board(0)(2) else " "
    }
}