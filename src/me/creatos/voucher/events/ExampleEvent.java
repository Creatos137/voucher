package me.creatos.voucher.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
//                                       Uncomment this part to make it cancellable
public class ExampleEvent extends Event  /*implements Cancellable */{
	
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    //Uncomment the line below to make it cancellable
    //private boolean isCancelled;

    public ExampleEvent(){
        //Uncomment the line below to make it cancellable
        //this.isCancelled = false;
    }


    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
    
    //Uncomment the method below to make it cancellable
    
//  @Override
//  public boolean isCancelled() {
//      return isCancelled;
//  }


  //Uncomment the line below to make it cancellable
  
//  @Override
//  public void setCancelled(boolean cancelled) {
//      this.isCancelled = cancelled;
//  }

}
