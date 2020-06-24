package command.commands;

import command.Command;
import db.DataBaseConnector;
import exchange.Response;
import runner.Editor;
import runner.ExecutorException;
import ticket.Ticket;
import ticket.TicketStorage;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;

public class UpdateCommand implements Command {
    @Override
    public Response execute(Editor editor) throws ExecutorException {
        if (editor.getValue() == null) {
            throw new ExecutorException("Не указан id.");
        }
        int id;
        try {
            id = Integer.parseInt(editor.getValue().trim());
        } catch (NumberFormatException e) {
            throw new ExecutorException("Неверный формат id: " + editor.getValue(), e);
        }
        Ticket tick = TicketStorage.getTickets().stream().filter(el -> el.getId() == id).findFirst().orElse(null);
        if (tick == null) {
            throw new ExecutorException("Элемент с id: " + id + " не найден.");
        }
        try {
            tick.setCreationDate(LocalDate.now());
            tick.setCost(tick.getcost() + 10);
        } catch (ValidationException e) {
            e.printStackTrace();
        }

        DataBaseConnector.updateTicket(tick);
        /*Ticket ticket;
        try {
            ticket = editor.getTicket();
        } catch (ValidationException e) {
            throw new ExecutorException(e);
        }
        if (ticket == null) {
            return new Response(true);
        }
        editor.getCollection().add(ticket);*/
        return new Response(true, "Элемент обновлен по id " + id);
    }
}
