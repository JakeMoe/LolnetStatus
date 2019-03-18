package com.gmail.jakesaddress.lolnetstatus.Commands;

import com.gmail.jakesaddress.lolnetstatus.Main;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

public class ReloadCommand implements CommandExecutor {


  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    Main.getInstance().initializeScoreboard();
    return CommandResult.success();
  }
}
