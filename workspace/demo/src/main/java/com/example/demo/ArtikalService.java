package com.example.demo;

import io.reactivex.rxjava3.core.*;

//import org.springframework.stereotype.Service;

//import io.reactivex.rxjava3.annotations.*;
//import io.reactivex.rxjava3.disposables.*;

import java.util.List;
import java.util.Arrays;

public class ArtikalService 
{

	private static final List<Artikal> Artikli = Arrays.asList
			(
				new Artikal("Mleko", 100, 10),
				new Artikal("Cokolada", 150, 20),
				new Artikal("Cips", 220, 15),
				new Artikal("Krem Bananica", 200, 12),
				new Artikal("Mentol Bombone", 300, 7),
				new Artikal("Brasno", 80, 18)
			);
	
	public Observable<Artikal> artikliNaProdaju() //vraca observable, ovim se obecava da ce doci neki podaci
										   //ali ako nema subscribera nista se ne vraca
	{
		return Observable.create
		(emitter ->      			 //emitter: salje podatke sa onNext(), baca error, ili salje COMPLETE(). 
			{
				int i = 0;
				System.out.println("Link start.");
				while (!emitter.isDisposed() && i< Artikli.size()) //isdisposed proverava da li je korisnik u medjuvremenu
																 //unsubscribe uradio, da zna da prestane da salje
																 //ako se unsubscribe na observable desio, isdisposed postaje true i ne salje se nista kroz data kanal
				{
					Artikal a = Artikli.get(i);      											//dobijamo prvi artikal u listi
					if (a.getSkladiste() == 0)
					{
						emitter.onError(new RuntimeException("Prazno skladiste. " + a));		
					}
					emitter.onNext(a);															 //salje se data tog artikla koji je getovan
					i++;
				}
				System.out.println("Link end.");
				emitter.onComplete(); 																	//kraj, nece se izvrsiti ako se error desio u toku slanja jer error zatvara sve komunikacione kanale
																										// tri kanala: DATA, ERROR, COMPLETE
			}
		);
	}
}
