package dev.gxlg.librgetter.services;

public record Dependency<L extends ServiceLoader<L>, T>(L serviceLoader, Export<L, T> export) { }
