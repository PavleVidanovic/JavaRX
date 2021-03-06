package com.example.demo;

import org.junit.jupiter.api.Test;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ArtikalServiceTest {
	
	@Test
	public void artikliNaProdaju() 
	{
		ArtikalService aService = new ArtikalService();
		
		Observable<Artikal> artikalObservable = aService.artikliNaProdaju();
		
		  artikalObservable.subscribe ( data -> System.out.println("Data received: " +
		  data), error -> System.out.println("An error has occured: " + error), () ->
		  System.out.println("Reactive link completed successfully."));
		 
		  System.out.println("");
		  System.out.println("Slucaj 2:");
		
		
		Observer<Artikal> observer = new Observer<Artikal>()
				{
					Disposable disposable;
					
					@Override
					public void onSubscribe(@NonNull Disposable d)
					{
						disposable = d;
					}
					
					@Override
					public void onNext(@NonNull Artikal a)
					{
						if (a.getIme().startsWith("Krem"))
							disposable.dispose(); //unsubscribe
						System.out.println("Data received: " + a);
					}
					
					@Override
					public void onError(@NonNull Throwable e)
					{
						System.out.println("Error observer: " + e);
					}
					
					@Override
					public void onComplete() 
					{
						System.out.println("Reactive link completed successfully.");
					}
				};
				
				artikalObservable.filter(a -> a.getSkladiste() > 10).subscribe(observer);
	}
	
}
