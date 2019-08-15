package br.com.markmv.model.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "TBL_AGENDAMENTO")
public class Marcacao {

	private Integer id;
	private Date data;
	private Date horaInicial;
	private Date horaFinal;
	private String descricao;
	private Usuario user;
	private Sala sala;

	public Marcacao() {
	}

	public Marcacao(Date data, Date horaInicial, Date horaFinal, String descricao) {
		this.data = data;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
		this.descricao = descricao;
	}

	public Marcacao(Date data, Date horaInicial, Date horaFinal) {
		super();
		this.data = data;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
	}

	@Id
	@Column(name = "ID_AGENDAMENTO")
	@SequenceGenerator(name = "S_AGENDAMENTO_ID", sequenceName = "S_AGENDAMENTO_ID")
	@GeneratedValue(generator = "S_AGENDAMENTO_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_AGENDAMENTO")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HR_INICIAL")
	public Date getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(Date horaInicial) {
		this.horaInicial = horaInicial;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "HR_FINAL")
	public Date getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}

	@Column(name = "DESCRICAO")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@ManyToOne
	@JoinColumn(name = "ID_MATRICULA")
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name = "ID_RECURSO")
	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	@Transient
	public boolean isPossivelMarcar() {
		return horaInicial.before(horaFinal);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marcacao other = (Marcacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
