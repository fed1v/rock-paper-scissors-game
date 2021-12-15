import de.vandermeer.asciitable.*;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class Table {
    String[] moves;

    public Table(String[] moves){
        this.moves = moves;
    }

    public String buildTable(){
        AsciiTable table = new AsciiTable();

        int numOfMoves = moves.length;

        table.addRule();

        String[] top = new String[numOfMoves + 1];
        top[0] = "";

        for(int i = 0; i < numOfMoves; i++){
            top[i + 1] = moves[i];
        }

        table.addRow(top);
        table.addRule();

        Rules rules = new Rules(moves);

        for(int i = 0; i < numOfMoves; i++){
            String[] row = new String[numOfMoves + 1];
            row[0] = moves[i];
            for(int j = 0; j < numOfMoves; j++){
                if(rules.rules[i][j] == 0) row[j + 1] = "Draw";
                else if(rules.rules[i][j] == 1) row[j + 1] = "Win";
                else if(rules.rules[i][j] == -1) row[j + 1] = "Lose";
            }
            table.addRow(row);
            table.addRule();
        }

        table.getContext().setWidth(125);
        table.setTextAlignment(TextAlignment.CENTER);

        return table.render();
    }
}
