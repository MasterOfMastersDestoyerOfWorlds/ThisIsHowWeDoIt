/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package resources;

public class setelemT {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected setelemT(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(setelemT obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  @SuppressWarnings("deprecation")
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        qhullJNI.delete_setelemT(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setP(SWIGTYPE_p_void value) {
    qhullJNI.setelemT_p_set(swigCPtr, this, SWIGTYPE_p_void.getCPtr(value));
  }

  public SWIGTYPE_p_void getP() {
    long cPtr = qhullJNI.setelemT_p_get(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_void(cPtr, false);
  }

  public void setI(int value) {
    qhullJNI.setelemT_i_set(swigCPtr, this, value);
  }

  public int getI() {
    return qhullJNI.setelemT_i_get(swigCPtr, this);
  }

  public setelemT() {
    this(qhullJNI.new_setelemT(), true);
  }

}
