package com.alu.omc.oam.log;

public interface ILogParser extends Cloneable
{
ParseResult parse(String log);

public ILogParser clone() throws CloneNotSupportedException;
}
