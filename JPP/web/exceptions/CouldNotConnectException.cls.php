<?php

class CouldNotConnectException extends Exception {
  
  
  public function CouldNotConnectException($message, $code = 0) {
    parent::__construct($message, $code);
    
  }

  public function __toString() {
    return __CLASS__ . ": [{$this->code}]: {$this->message}\n";
  }
}


?>