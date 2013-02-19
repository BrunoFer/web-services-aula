#!/usr/bin/perl
#udpclient.pl

use IO::Socket::INET;
use XML::Simple;
use Data::Dumper;

my ($socket,$data);

$socket = new IO::Socket::INET (
	PeerAddr   => '127.0.0.1:5000',
	Proto        => 'udp'
) or die "Erro na criação do socket : $!\n";

while (1)
{
	#operação de envio
	print "\nFale o CEP: ";
	$data = <>;
	chomp $data;
	$socket->send($data);

	#operação de leitura
	$socket->recv($data,1024);
	$host = $socket->peerhost();
	$port = $socket->peerport();
	my $webservicecep = XMLin($data);
	$resultado = $webservicecep->{resultado};
	
	if ($resultado==1){
		print "\nLogradouro: $webservicecep->{tipo_logradouro} $webservicecep->{logradouro}\n";
		print "Bairro: $webservicecep->{bairro}\n";
		print "Cidade: $webservicecep->{cidade}\n";
		print "UF: $webservicecep->{uf}\n";
	} elsif ($resultado==2){
		print "\nCidade: $webservicecep->{cidade}\n";
		print "UF: $webservicecep->{uf}\n";
	} else {
		print "\nCEP inválido!\n";
	}

}

$socket->close();
