package org.haw.vs.praktikum.gwln.praktikum2.b.bank;

import org.haw.vs.praktikum.gwln.datatypes.bounded.MutableBoundedInteger;

public class Account {
	private String _player;
	private MutableBoundedInteger _saldo;
	
	public Account(String player, int saldo) {
		_player = player;
		_saldo = new MutableBoundedInteger(0, Integer.MAX_VALUE, saldo);
	}
	
	public String getPlayer() {
		return _player;
	}
	
	public int getSaldo() {
		return _saldo.getValue();
	}
	
	void setSaldo(int saldo) {
		_saldo.setValue(saldo);
	}
	
	void alterSaldo(int amount) {
		_saldo.alterValue(amount);
	}
}