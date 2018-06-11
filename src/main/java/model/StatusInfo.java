package model;

import javax.persistence.Embeddable;

@Embeddable
public enum StatusInfo {
	DELAYED, CANCELLED, ONTIME
}
