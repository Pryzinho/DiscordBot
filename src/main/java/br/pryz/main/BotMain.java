package br.pryz.main;

import br.pryz.commands.Ping;
import br.pryz.api.commandhandler.api.command.CommandBuilder;
import br.pryz.api.commandhandler.api.handler.CommandHandler;
import br.pryz.api.commandhandler.api.handler.CommandHandlerBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BotMain {

    public static void main(String[] args) {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("C:\\Users\\Jhefferson Souza\\Desktop\\PryzatMinecraft\\src\\main\\resources\\discord_credentials.json"));
            System.out.printf("Nome do Bot: %s \n Autor: %s \n Prefixo: %s \n",
                    jsonObject.get("name"), jsonObject.get("author"), jsonObject.get("prefix"));
            JDABuilder jdaBuilder = JDABuilder.createDefault((String) jsonObject.get("accessToken"));
            jdaBuilder.setActivity(Activity.of(Activity.ActivityType.DEFAULT, (String) jsonObject.get("website")));
            JDA jda = jdaBuilder.build();

            CommandHandlerBuilder cmdbuilder = new CommandHandlerBuilder(jda);
            cmdbuilder.setPrefix((String) jsonObject.get("prefix"));
            cmdbuilder.addCommand(
                    new CommandBuilder("ping", new Ping()).build()
            );
            System.out.println("Carregando comandos...");
            String cmds = "";
            CommandHandler cmdhandler = cmdbuilder.build();
            for (int i = 0; i < cmdhandler.getCommandList().size(); i++) {
                String cmdname = cmdhandler.getCommandList().get(i).getCommandName();

                if (i == cmdhandler.getCommandList().size() - 1) {
                    cmds = cmds + cmdname + ".";
                } else {
                    cmds = cmds + cmdname + ", ";
                }
            }
            System.out.println("Quantidade de comandos encontrados: " + cmdhandler.getCommandList().size());
            System.out.println("Comandos carregados: " + cmds);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        System.out.println("Bot iniciado com sucesso!");

    }

}
