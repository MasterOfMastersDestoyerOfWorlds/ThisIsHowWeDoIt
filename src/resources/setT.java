/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.2
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package resources;

public class setT {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected setT(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(setT obj) {
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
        qhullJNI.delete_setT(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setMaxsize(int value) {
    qhullJNI.setT_maxsize_set(swigCPtr, this, value);
  }

  public int getMaxsize() {
    return qhullJNI.setT_maxsize_get(swigCPtr, this);
  }

  public void setE(setelemT value) {
    qhullJNI.setT_e_set(swigCPtr, this, setelemT.getCPtr(value), value);
  }

  public setelemT getE() {
    long cPtr = qhullJNI.setT_e_get(swigCPtr, this);
    return (cPtr == 0) ? null : new setelemT(cPtr, false);
  }

  public setT() {
    this(qhullJNI.new_setT(), true);
  }

}
