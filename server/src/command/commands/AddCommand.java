package command.commands;

import command.Command;
import exchange.Request;
import exchange.Response;
import ticket.Ticket;
import runner.Editor;
import runner.ExecutorException;

public final class AddCommand implements Command {
    private Ticket ticket;
    public void addRequest(Request request){
        this.ticket = request.getTicket();
    }
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        return new Response(true, "Добавление нового элемента прошло успешно.");
    }
}