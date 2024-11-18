package com.devocean.Balbalm.global;

public interface UseCase<I extends UseCase.Command, O extends UseCase.Result> {
	O execute(final I input);

	interface Command {}
	interface Result {}
}

