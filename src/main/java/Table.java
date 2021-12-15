import de.vandermeer.asciitable.*;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

public class Table {
    String[] moves;

    public Table(String[] moves){
        this.moves = moves;
    }


    public String buildTable(){
        String result = "";
        AsciiTable table = new AsciiTable();

        String[] args = moves;
        int numOfArgs = args.length;

        table.addRule();

        String[] top = new String[numOfArgs + 1];
        top[0] = "";

        for(int i = 0; i < numOfArgs; i++){
            top[i + 1] = args[i];
        }

        table.addRow(top);
        table.addRule();

        Rules rules = new Rules(args);

        rules.showTable();
        System.out.println();

        for(int i = 0; i < numOfArgs; i++){
            String[] row = new String[numOfArgs + 1];
            row[0] = args[i];
            for(int j = 0; j < numOfArgs; j++){
                if(rules.rules[i][j] == 0) row[j + 1] = "Draw";
                else if(rules.rules[i][j] == 1) row[j + 1] = "Win";
                else if(rules.rules[i][j] == -1) row[j + 1] = "Lose";
            }
            table.addRow(row);
            table.addRule();
        }

        table.getContext().setWidth(125);
        table.setTextAlignment(TextAlignment.CENTER);

        result = table.render();

        return result;
    }

}
